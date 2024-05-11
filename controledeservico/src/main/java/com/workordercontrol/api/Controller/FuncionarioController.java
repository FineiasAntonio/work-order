package com.workordercontrol.api.Controller;

import java.util.List;
import java.util.UUID;

import com.workordercontrol.api.Domain.Service.EmployeeService;
import com.workordercontrol.api.Infra.Entity.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/funcionarios")
public class FuncionarioController {
    
    @Autowired
    private EmployeeService service;

    @GetMapping
    public ResponseEntity<List<Employee>> getAll(){
        return ResponseEntity.status(HttpStatus.OK).body(service.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Employee> getById(@PathVariable UUID id){
        Employee funcionario = service.getById(id);
        return ResponseEntity.status(HttpStatus.OK).body(funcionario);
    }

    @PostMapping
    public ResponseEntity<Employee> create(Employee funcionario){
        Employee funcionarioCriado = service.create(funcionario);
        return ResponseEntity.status(HttpStatus.CREATED).body(funcionarioCriado);
    }

    @DeleteMapping
    public ResponseEntity<Void> delete(int id){
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
    }

    @PutMapping
    public ResponseEntity<Employee> update(int id, Employee funcionario){
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
    }

}