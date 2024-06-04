package com.workordercontrol.api.Domain.Service;

import com.workordercontrol.api.Infra.DTO.NotificationDTO;
import com.workordercontrol.api.Infra.Entity.Product;
import com.workordercontrol.api.Infra.Entity.ReservedProduct;
import com.workordercontrol.api.Infra.Entity.Status;
import com.workordercontrol.api.Infra.Entity.WorkOrder;
import com.workordercontrol.api.Infra.Repository.WorkOrderRepository;
import com.workordercontrol.api.Infra.Repository.StorageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.BiPredicate;
import java.util.stream.Collectors;

@Service
public class NotificationService {

    @Autowired
    private StorageRepository produtoRepository;
    @Autowired
    private WorkOrderRepository osRepository;

    private static List<NotificationDTO> notificationPool = new LinkedList<>();

    public List<NotificationDTO> getNotificationPool() {
        verifyNewNotifications();
        return notificationPool;
    }

    public void verifyNewNotifications() {
        notificationPool.clear();

        BiPredicate<Product, ReservedProduct> verify = (x, t) -> x.getProductId().equals(t.getProductId());

        Map<UUID, Product> allAvaiableProducts = produtoRepository.findByQuantityGreaterThan(0).stream().collect(Collectors.toMap(Product::getProductId, product -> product));
        List<WorkOrder> activeWorkOrders = osRepository.findByStatus(Status.AWATING_PARTS);

        for (WorkOrder workOrder : activeWorkOrders) {
            Set<ReservedProduct> currentWorkOrderReservedProducts = new HashSet<>(workOrder.getReserve().getReservedProducts().values());

            Set<Product> matchedProducts = allAvaiableProducts.values()
                    .stream()
                    .filter(e -> currentWorkOrderReservedProducts.stream().anyMatch(t -> verify.test(e,t)))
                    .collect(Collectors.toSet());

            notificationPool.addAll(
                    currentWorkOrderReservedProducts.stream()
                            .filter(expectedProduct ->
                                    matchedProducts.stream()
                                            .anyMatch(storagedProduct ->
                                                    expectedProduct.getProductId().equals(storagedProduct.getProductId()) &&
                                                            storagedProduct.getQuantity() >= expectedProduct.getRequiredQuantity()
                                            )
                            )
                            .map(expectedProduct -> NotificationDTO.builder()
                                    .productId(expectedProduct.getProductId())
                                    .workOrderId(workOrder.getWorkOrderId())
                                    .product(expectedProduct.getName())
                                    .quantity(expectedProduct.getRequiredQuantity())
                                    .clientName(workOrder.getClient().getName())
                                    .build()
                            )
                            .toList()
            );

        }
    }
}
