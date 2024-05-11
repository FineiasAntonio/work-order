package com.workordercontrol.api.Domain.Service;

import java.util.List;
import java.util.UUID;

import com.workordercontrol.api.Infra.DTO.Employee.EmployeeRequest;
import com.workordercontrol.api.Util.DataUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.workordercontrol.api.Infra.Entity.Employee;
import com.workordercontrol.api.Exception.CustomExceptions.NotFoundException;
import com.workordercontrol.api.Infra.Repository.FuncionarioRepository;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
@Qualifier("FuncionarioService")
public class EmployeeService {

    @Autowired
    private FuncionarioRepository repository;

    @Autowired
    private WorkOrderService workOrderService;

    public List<Employee> getAll() {
        return repository.findAll();
    }

    public Employee getById(UUID id) {
        return repository.findById(id).orElseThrow(() -> new NotFoundException("Employee hasn't been found"));
    }

    @Transactional
    public Employee create(EmployeeRequest request) {
        Employee mappedEmployee = Employee.builder()
                .name(request.name())
                .email(request.email())
                .number(request.number())
                .build();

        Employee registeredEmployee = repository.save(mappedEmployee);
        log.info("Resgistered employee: " + registeredEmployee.getName());
        return registeredEmployee;
    }

    @Transactional
    public void update(UUID employeeId, EmployeeRequest request) {
        Employee selectedEmployee = repository.findById(employeeId)
                .orElseThrow(() -> new NotFoundException("Employee hasn't been found"));
        DataUtils.copyData(request, selectedEmployee, "employeeId");

        repository.save(selectedEmployee);
        log.info("Employee sucessful updated");
    }

    @Transactional
    public void delete(UUID id) {
        workOrderService.getAll()
                .stream()
                .filter(workOrder -> workOrder.getEmployee().getEmployeeId().equals(id))
                .forEach(workOrder -> workOrderService.delete(workOrder.getWorkOrderId()));

        repository.deleteById(id);
    }


}
