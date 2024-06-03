package com.workordercontrol.api.Controller;

import com.workordercontrol.api.Domain.Service.WorkOrderService;
import com.workordercontrol.api.Exception.ExceptionDTO;
import com.workordercontrol.api.Infra.DTO.OS.WorkOrderCreateRequest;
import com.workordercontrol.api.Infra.DTO.OS.WorkOrderUpdateRequest;
import com.workordercontrol.api.Infra.Entity.WorkOrder;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
@RequestMapping("/workorder")
public class WorkOrderController {

    @Autowired
    private WorkOrderService service;

    @GetMapping
    @ApiResponses(
            @ApiResponse(
                    responseCode = "200",
                    description = "Return all work order",
                    content = {@Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = WorkOrder.class)))}
            )
    )
    public ResponseEntity<List<WorkOrder>> getAll() {
        List<WorkOrder> workOrderList = service.getAll();
        return ResponseEntity.status(HttpStatus.OK).body(workOrderList);
    }

    @GetMapping("/{workOrderId}")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Return a specific work order by id",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = WorkOrder.class))}

            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Return not found error if doesn't have any work order with the ID passed by the user",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDTO.class))}
            )
    }
    )
    public ResponseEntity<WorkOrder> getById(@PathVariable int workOrderId) {
        WorkOrder workOrder = service.getById(workOrderId);
        return ResponseEntity.status(HttpStatus.OK).body(workOrder);
    }

    @PostMapping
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Register a new work order and return its complete data",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = WorkOrder.class))}
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Return bad request error if the request has some invalid field",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDTO.class))}
            )
    }
    )
    public ResponseEntity<WorkOrder> create(@RequestBody WorkOrderCreateRequest workOrderRequest) {
        WorkOrder createdWorkOrder = service.create(workOrderRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdWorkOrder);
    }

    @DeleteMapping("/{workOrderId}")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description = "Delete a specific work order by id"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Return not found error if doesn't have any work order with the ID passed by the user",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDTO.class))}
            )}
    )
    public ResponseEntity<Void> delete(@PathVariable int workOrderId) {
        service.delete(workOrderId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PutMapping("/{workOrderId}")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Update an existing work order and return its complete data",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = WorkOrder.class))}
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Return bad request error if request has some invalid field",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDTO.class))}
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Return not found error if doesn't have any work order with the ID passed by the user",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDTO.class))}
            )
    }
    )
    public ResponseEntity<WorkOrder> update(@PathVariable int workOrderId, @RequestBody WorkOrderUpdateRequest workOrderRequest) {
        WorkOrder updatedWorkOrder = service.update(workOrderId, workOrderRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(updatedWorkOrder);
    }

    @GetMapping("/print/{workOrderId}")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Return a document that can be used for print work order details",
                    content = {@Content(mediaType = "text/html")}
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Return not found error if doesn't have any work order with the ID passed by the user",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDTO.class))}
            )
    }
    )
    public ResponseEntity<ModelAndView> getDocDetails(@PathVariable("id") int workOrderId) {
        ModelAndView workOrderDocument = new ModelAndView("doc");
        WorkOrder workOrder = service.getById(workOrderId);
        workOrderDocument.addObject("os", workOrder);
        return ResponseEntity.status(HttpStatus.OK).body(workOrderDocument);
    }

}
