package com.workordercontrol.api.Domain.Mapper;

import com.workordercontrol.api.Domain.Service.ClientService;
import com.workordercontrol.api.Exception.CustomExceptions.NotFoundException;
import com.workordercontrol.api.Infra.DTO.OS.WorkOrderCreateRequest;
import com.workordercontrol.api.Infra.DTO.OS.WorkOrderUpdateRequest;
import com.workordercontrol.api.Infra.Entity.Client;
import com.workordercontrol.api.Infra.Entity.WorkOrder;
import com.workordercontrol.api.Infra.Entity.Reserve;
import com.workordercontrol.api.Infra.Entity.Status;

import com.workordercontrol.api.Infra.Repository.EmployeeRepository;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.concurrent.FutureTasks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.time.LocalDate;
import java.util.concurrent.*;

@Component
@Log4j2
public class WorkOrderMapper {
    @Autowired
    private ReserveMapper reserveMapper;
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private ClientService clientService;


    public WorkOrder createMap(WorkOrderCreateRequest dto) throws ExecutionException, InterruptedException, TimeoutException {

        Future<Reserve> workOrderReserveTask = FutureTasks.run(() -> reserveMapper.createReserve(dto.reserve()));

        Client workOrderClient;
        if (dto.client().clientId() == null){
            workOrderClient = clientService.create(dto.client());
        } else {
            workOrderClient = clientService.getById(dto.client().clientId());
        }

        WorkOrder workOrder = WorkOrder.builder()
                .client(workOrderClient)
                .equipment(dto.equipment())
                .serialNumber(dto.serialNumber())
                .service(dto.service())
                .notes(dto.notes())
                .exceptedDate(Date.valueOf(dto.exceptedDate().toLocalDate()))
                .employee(employeeRepository.findById(dto.employeeId()).orElseThrow(() -> new NotFoundException("Employee hasn't been found")))
                .createdAt(Date.valueOf(LocalDate.now()))
                .build();

        if (workOrderReserveTask.isDone()){
            workOrder.setReserve(workOrderReserveTask.get(2L, TimeUnit.SECONDS));
        }

        if (workOrder.getReserve().isActive()) {
            workOrder.setStatus(Status.AWATING_PARTS);
            workOrder.setTotalValue(updateTotalValue(workOrder.getReserve()));
        } else {
            workOrder.setStatus(Status.ONGOING);
        }

        return workOrder;
    }

    public WorkOrder updateMap(WorkOrder workOrder, WorkOrderUpdateRequest dto) throws ExecutionException, InterruptedException, TimeoutException {

        Future<Reserve> workOrderReserveTask = FutureTasks.run(() -> reserveMapper.updateReserve(workOrder.getReserve(), dto.reserve()));

        workOrder.setEquipment(dto.equipment());
        workOrder.setSerialNumber(dto.serialNumber());
        workOrder.setService(dto.service());
        workOrder.setNotes(dto.notes());
        workOrder.setExceptedDate(Date.valueOf(dto.exceptedDate().toLocalDate()));
        workOrder.setEmployee(employeeRepository.findById(dto.employeeId()).orElseThrow(() -> new NotFoundException("Employee hasn't been found")));

        if (workOrderReserveTask.isDone()){
            workOrder.setReserve(workOrderReserveTask.get(2L, TimeUnit.SECONDS));
        }

        if (dto.finished()) {
            workOrder.setStatus(Status.FINISHED);
            workOrder.setExceptedDate(Date.valueOf(LocalDate.now()));
        } else {
            if (workOrder.getReserve().isActive()) {
                workOrder.setStatus(Status.AWATING_PARTS);
                workOrder.setTotalValue(updateTotalValue(workOrder.getReserve()));
            } else {
                workOrder.setStatus(Status.ONGOING);
            }
            workOrder.setFinishedAt(null);
            workOrder.setDeliveredAt(null);
        }

        return workOrder;
    }

    private double updateTotalValue(Reserve reserve) {
        return reserve.getReservedProducts()
                .values()
                .stream()
                .mapToDouble(x -> x.getUnitPrice() * x.getRequiredQuantity())
                .reduce(0, Double::sum) + reserve.getMaoDeObra();
    }
}
