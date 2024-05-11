package com.workordercontrol.api.Domain.Service;

import com.workordercontrol.api.Exception.CustomExceptions.NotFoundException;
import com.workordercontrol.api.Infra.DTO.ClientDTO.ClientRequest;
import com.workordercontrol.api.Infra.DTO.Employee.EmployeeRequest;
import com.workordercontrol.api.Infra.Entity.Client;
import com.workordercontrol.api.Infra.Entity.Employee;
import com.workordercontrol.api.Infra.Repository.ClientRepository;
import com.workordercontrol.api.Util.DataUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Log4j2
@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private WorkOrderService workOrderService;

    public List<Client> getAll() {
        return clientRepository.findAll();
    }

    public Client getById(UUID id) {
        return clientRepository.findById(id).orElseThrow(() -> new NotFoundException("Client hasn't been found"));
    }

    @Transactional
    public Client create(ClientRequest request) {
        Client mappedClient = Client.builder()
                .name(request.name())
                .identity(request.identity())
                .email(request.email())
                .number(request.number())
                .build();

        Client registeredClient = clientRepository.save(mappedClient);
        log.info("Resgistered client: " + registeredClient.getName());
        return registeredClient;
    }

    @Transactional
    public void update(UUID clientId, ClientRequest request) {
        Client selectedClient = clientRepository.findById(clientId)
                .orElseThrow(() -> new NotFoundException("Client hasn't been found"));
        DataUtils.copyData(request, selectedClient, "clientId");

        clientRepository.save(selectedClient);
        log.info("Client sucessful updated");
    }

    @Transactional
    public void delete(UUID id) {
        workOrderService.getAll()
                .stream()
                .filter(workOrder -> workOrder.getEmployee().getEmployeeId().equals(id))
                .forEach(workOrder -> workOrderService.delete(workOrder.getWorkOrderId()));

        clientRepository.deleteById(id);
    }

}
