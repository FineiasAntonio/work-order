package com.workordercontrol.api.Domain.Service;

import com.workordercontrol.api.Infra.DTO.NotificationDTO;
import com.workordercontrol.api.Infra.Repository.WorkOrderRepository;
import com.workordercontrol.api.Infra.Repository.StorageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.BiPredicate;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private StorageRepository produtoRepository;
    private WorkOrderRepository osRepository;

    private static List<NotificationDTO> notificationPool = new LinkedList<>();

    public List<NotificationDTO> getNotificationPool() {
//        verifyNewNotifications();
        return notificationPool;
    }

//    @Scheduled(fixedDelay = 2, timeUnit = TimeUnit.HOURS)
//    public void verifyNewNotifications() {
//        notificationPool.clear();
//
//        BiPredicate<Product, ReservedProduct> verify = (x, t) -> x.getId().equals(t.getId());
//
//        Set<Product> AllAvaiableProducts = produtoRepository.findByQuantidadeGreaterThan(0);
//        List<WorkOrder> active = osRepository.findBySituacao(Status.AGUARDANDO_PECA);
//
//        for (WorkOrder order : active) {
//            Set<ReservedProduct> CurrentOrderProducts = new HashSet<>(order.getReserva().getProdutos_reservados());
//
//            Set<Product> MatchedProducts = AllAvaiableProducts.stream()
//                    .filter(e -> CurrentOrderProducts.stream().anyMatch(t -> verify.test(e,t)))
//                    .collect(Collectors.toSet());
//
//
//            notificationPool.addAll(
//                    CurrentOrderProducts.stream()
//                            .filter(expectedProduct ->
//                                    MatchedProducts.stream()
//                                            .anyMatch(storagedProduct ->
//                                                    expectedProduct.getId().equals(storagedProduct.getId()) &&
//                                                            storagedProduct.getQuantidade() >= expectedProduct.getQuantidadeNescessaria()
//                                            )
//                            )
//                            .map(expectedProduct -> NotificationDTO.builder()
//                                    .uuid(expectedProduct.getId())
//                                    .orderID(order.getId())
//                                    .produto(expectedProduct.getProduto())
//                                    .quantidade(expectedProduct.getQuantidadeNescessaria())
//                                    .nomeCliente(order.getNome())
//                                    .build()
//                            )
//                            .toList()
//            );
//
//        }
//
//        notificationPool.sort(Collections.reverseOrder(new Comparator<NotificationDTO>() {
//            @Override
//            public int compare(NotificationDTO o1, NotificationDTO o2) {
//                WorkOrder RelatedOrder1 = osRepository.findById(o1.orderID()).get();
//                WorkOrder RelatedOrder2 = osRepository.findById(o2.orderID()).get();
//                return Double.compare(RelatedOrder1.getValorTotal(), RelatedOrder2.getValorTotal());
//            }
//        }));
//
//    }
}
