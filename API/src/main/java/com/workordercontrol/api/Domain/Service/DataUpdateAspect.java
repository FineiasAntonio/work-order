package com.workordercontrol.api.Domain.Service;

import com.workordercontrol.api.Infra.Entity.Product;
import com.workordercontrol.api.Infra.Entity.Status;
import com.workordercontrol.api.Infra.Repository.ReserveRepository;
import com.workordercontrol.api.Infra.Repository.StorageRepository;
import com.workordercontrol.api.Infra.Repository.WorkOrderRepository;
import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Aspect
@Log4j2
public class DataUpdateAspect {

    @Autowired
    private StorageRepository storageRepository;
    @Autowired
    private WorkOrderRepository workOrderRepository;
    @Autowired
    private ReserveRepository reserveRepository;

    @After(
            "execution(* com.workordercontrol.api.Domain.Service.*.create()) || " +
            "execution(* com.workordercontrol.api.Domain.Service.*.update()) ||" +
            "execution(* com.workordercontrol.api.Domain.Service.*.delete())"
    )
    public void updateInfos(){
        updateProductsValues();
        updateWorkOrderStatus();
        log.info("Updated datas");
    }

    private void updateProductsValues() {

        reserveRepository.findByActive(true).forEach(reserve -> {
            reserve.getReservedProducts().values().forEach(reservedProduct -> {
                Product productFormStorage = storageRepository.findById(reservedProduct.getProductId()).get();
                BeanUtils.copyProperties(productFormStorage, reservedProduct, "quantity", "requiredQuantity");
            });
            reserveRepository.save(reserve);
        });
    }

    private void updateWorkOrderStatus() {

        workOrderRepository.findByStatus(Status.AWATING_PARTS).stream()
                .filter(workOrder -> workOrder.getReserve().getReservedProducts().values()
                        .stream()
                        .allMatch(reservedProduct -> reservedProduct.getQuantity() == reservedProduct.getRequiredQuantity()))
                .forEach(workOrder -> {
                    workOrder.setStatus(Status.ONGOING);
                    workOrder.getReserve().setActive(false);
                    workOrderRepository.save(workOrder);;
                });
    }
}
