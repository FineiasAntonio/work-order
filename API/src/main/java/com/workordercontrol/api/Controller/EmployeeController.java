package com.workordercontrol.api.Controller;

import java.util.List;
import java.util.UUID;

import com.workordercontrol.api.Domain.Service.EmployeeService;
import com.workordercontrol.api.Exception.ExceptionDTO;
import com.workordercontrol.api.Infra.DTO.Employee.EmployeeRequest;
import com.workordercontrol.api.Infra.Entity.Employee;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService service;

    @GetMapping
    @ApiResponses(
            @ApiResponse(
                    responseCode = "200",
                    description = "Return all employees",
                    content = {@Content(mediaType = "application/json")}
            )
    )
    public ResponseEntity<List<Employee>> getAll() {
        return ResponseEntity.status(HttpStatus.OK).body(service.getAll());
    }

    @GetMapping("/{employeeId}")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Return a specific employee by id",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Employee.class))}
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Return not found error if doesn't have any employee with the ID passed by the user",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDTO.class))}
            )
    }
    )
    public ResponseEntity<Employee> getById(@PathVariable UUID employeeId) {
        Employee employee = service.getById(employeeId);
        return ResponseEntity.status(HttpStatus.OK).body(employee);
    }

    @PostMapping
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Register a new employee and return its complete data",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Employee.class))}
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Return bad request error if the request has some invalid fields",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDTO.class))}
            )
    }
    )
    public ResponseEntity<Employee> create(EmployeeRequest employeeRequest) {
        Employee createdEmployee = service.create(employeeRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdEmployee);
    }

    @DeleteMapping("/{employeeId}")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description = "Delete a specific employee by id and all work order in him name"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Return not found error if doesn't have any employee with the ID passed by the user",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDTO.class))}
            )}
    )
    public ResponseEntity<Void> delete(@PathVariable UUID employeeId) {
        service.delete(employeeId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PutMapping("/{employeeId}")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Update an existing employee and return its complete data",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Employee.class))}
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Return bad request error if request has some invalid field",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDTO.class))}
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Return not found error if doesn't have any employee with the ID passed by the user",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDTO.class))}
            )
    }
    )
    public ResponseEntity<Employee> update(@PathVariable UUID employeeId, EmployeeRequest employeeRequest) {
        service.update(employeeId, employeeRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}
