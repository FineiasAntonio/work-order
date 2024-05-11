package com.workordercontrol.api.Domain.Mapper;

import com.workordercontrol.api.Domain.Service.ClientService;
import com.workordercontrol.api.Domain.Service.EmployeeService;
import com.workordercontrol.api.Exception.CustomExceptions.BadRequestException;
import com.workordercontrol.api.Exception.CustomExceptions.NotFoundException;
import com.workordercontrol.api.Infra.DTO.OS.WorkOrderCreateRequest;
import com.workordercontrol.api.Infra.DTO.OS.WorkOrderUpdateRequest;
import com.workordercontrol.api.Infra.Entity.Client;
import com.workordercontrol.api.Infra.Repository.FuncionarioRepository;
import com.workordercontrol.api.Infra.Entity.WorkOrder;
import com.workordercontrol.api.Infra.Entity.Reserve;
import com.workordercontrol.api.Infra.Entity.Status;

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
    private EmployeeService employeeService;
    @Autowired
    private ClientService clientService;


    public WorkOrder map(WorkOrderCreateRequest dto) throws ExecutionException, InterruptedException, TimeoutException {

        Future<Reserve> workOrderReserveTask = FutureTasks.run(() -> reserveMapper.criarReserva(dto.reserva()));

        Client workOrderClient;
        if (dto.client().clientId() == null){
            workOrderClient = clientService.create(dto.client());
        } else {
            workOrderClient = clientService.getById(dto.client().clientId());
        }

        WorkOrder workOrder = WorkOrder.builder()
                .client(workOrderClient)
                .equipment(dto.equipamento())
                .serialNumber(dto.numeroSerie())
                .service(dto.servico())
                .notes(dto.observacao())
                .exceptedDate(Date.valueOf(dto.dataSaida()))
                .employee(employeeService.getById(dto.funcionarioId()))
                .createdAt(Date.valueOf(LocalDate.now()))
                .build();

        if (workOrderReserveTask.isDone()){
            workOrder.setReserve(workOrderReserveTask.get(2L, TimeUnit.SECONDS));
        }

        if (workOrder.getReserve().isActive()) {
            workOrder.setStatus(Status.AGUARDANDO_PECA);
            workOrder.setTotalValue(atualizarValorOS(workOrder.getReserve()));
        } else {
            workOrder.setStatus(Status.EM_ANDAMENTO);
        }

        return workOrder;
    }

    public WorkOrder updateMap(WorkOrder ordemdeservico, WorkOrderUpdateRequest dto) {
//
//        ordemdeservico.setEquipamento(dto.equipamento());
//        ordemdeservico.setNumeroSerie(dto.numeroSerie());
//        ordemdeservico.setServico(dto.servico());
//        ordemdeservico.setObservacao(dto.observacao());
//        ordemdeservico.setComentarios(dto.comentarios());
//        ordemdeservico.setDataSaida(Date.valueOf(dto.dataSaida().toLocalDate()));
//        ordemdeservico.setSubSituacao(dto.subSituacao());
//        ordemdeservico.setFuncionario(funcionarioRepository.findById(dto.funcionarioId()).orElseThrow(() -> new NotFoundException("Employee not found")));
//
//        ordemdeservico.setReserva(reservaMapper.atualizarReserva(
//                ordemdeservico.getReserva(),
//                dto.reserva()
//        ));
//
//        if (dto.concluido()) {
//            ordemdeservico.setSituacao(Status.CONCLUIDO);
//            ordemdeservico.setDataConclusao(Date.valueOf(LocalDate.now()));
//            if (dto.subSituacao() == SubSituacao.ENTREGUE) {
//                ordemdeservico.setDataEntrega(Date.valueOf(LocalDate.now()));
//            }
//        } else {
//            if (ordemdeservico.getReserva().isAtivo()) {
//                ordemdeservico.setSituacao(Status.AGUARDANDO_PECA);
//                ordemdeservico.setValorTotal(atualizarValorOS(ordemdeservico.getReserva()));
//            } else {
//                ordemdeservico.setSituacao(Status.EM_ANDAMENTO);
//            }
//            ordemdeservico.setDataConclusao(null);
//            ordemdeservico.setDataEntrega(null);
//        }
//
        return ordemdeservico;
    }

    private double atualizarValorOS(Reserve reserve) {
        return reserve.getReservedProducts()
                .values()
                .stream()
                .mapToDouble(x -> x.getUnitPrice() * x.getQuantidadeNescessaria())
                .reduce(0, Double::sum) + reserve.getMaoDeObra();
    }
}
