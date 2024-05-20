package com.workordercontrol.api.Domain.Service;

import com.workordercontrol.api.Infra.DTO.OS.WorkOrderCreateRequest;
import com.workordercontrol.api.Infra.DTO.OS.WorkOrderUpdateRequest;
import com.workordercontrol.api.Domain.Mapper.WorkOrderMapper;
import com.workordercontrol.api.Exception.CustomExceptions.NotFoundException;
import com.workordercontrol.api.Infra.Entity.Product;
import com.workordercontrol.api.Infra.Entity.Reserve;
import com.workordercontrol.api.Infra.Entity.ReservedProduct;
import com.workordercontrol.api.Infra.Entity.WorkOrder;
import com.workordercontrol.api.Infra.Repository.StorageRepository;
import com.workordercontrol.api.Infra.Repository.WorkOrderRepository;
import com.workordercontrol.api.Infra.Repository.ReservaRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

@Log4j2
@Service
public class WorkOrderService {

    @Autowired
    private WorkOrderRepository repository;
    @Autowired
    private ReservaRepository reserveRepository;
    @Autowired
    private StorageRepository storageRepository;
    @Autowired
    private WorkOrderMapper mapper;

    public List<WorkOrder> getAll() {
        return repository.findAll();
    }

    public WorkOrder getById(int id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Work order hasn't been found"));
    }

    @Transactional
    public WorkOrder create(WorkOrderCreateRequest workorder) {
        WorkOrder mappedWorkOrder;
        try {
            mappedWorkOrder = mapper.createMap(workorder);
        } catch (ExecutionException | InterruptedException | TimeoutException e) {
            throw new RuntimeException(e);
        }

        repository.save(mappedWorkOrder);
        log.info("Work order nº{} sucessful registered | Owner: {} | Date: {}", mappedWorkOrder.getWorkOrderId(), mappedWorkOrder.getEmployee(), mappedWorkOrder.getCreatedAt());
        return mappedWorkOrder;
    }

    @Transactional
    public void delete(int workOrderId) {

        Reserve workOrderReserve = reserveRepository.findByWorkOrderId(workOrderId);

        if (workOrderReserve.isActive()) {
            Iterator<Map.Entry<UUID, ReservedProduct>> iterator = workOrderReserve.getReservedProducts().entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<UUID, ReservedProduct> entry = iterator.next();
                ReservedProduct reservedProduct = entry.getValue();
                Optional<Product> productOptional = storageRepository.findById(reservedProduct.getProductId());

                if (productOptional.isPresent()) {
                    Product storagedProduct = productOptional.get();
                    storagedProduct.setQuantity(storagedProduct.getQuantity() + reservedProduct.getQuantity());
                    storageRepository.save(storagedProduct);
                } else {
                    iterator.remove();
                }
            }
        }

        repository.deleteById(workOrderId);
        log.info("Work order nº{} sucessful removed", workOrderId);
    }

    @Transactional
    public WorkOrder update(int id, WorkOrderUpdateRequest os) {
        try {
            WorkOrder workOrder = repository.findById(id)
                    .orElseThrow(() -> new NotFoundException("Work order hasn't been found"));
            WorkOrder updatedWorkOrder = mapper.updateMap(workOrder, os);

            repository.save(updatedWorkOrder);
            log.info("Work order nº{} sucessful updated", id);
            return updatedWorkOrder;

        } catch (ExecutionException | InterruptedException | TimeoutException e) {
            throw new RuntimeException(e);
        }
    }
}
