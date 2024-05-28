package com.workordercontrol.api.Controller;

import com.workordercontrol.api.Domain.Service.ReservaService;
import com.workordercontrol.api.Exception.ExceptionDTO;
import com.workordercontrol.api.Infra.DTO.Reserva.ReserveProductsRequest;
import com.workordercontrol.api.Infra.Entity.Reserve;

import java.util.List;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/reserve")
public class ReserveController {

    @Autowired
    private ReservaService reserveService;

    @GetMapping()
    @ApiResponses(
            @ApiResponse(
                    responseCode = "200",
                    description = "Return all reserves",
                    content = {@Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Reserve.class)))}
            )
    )
    public ResponseEntity<List<Reserve>> listAllReserves() {
        return ResponseEntity.status(HttpStatus.OK).body(reserveService.getAll());
    }

    @PostMapping("/{os}")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description = "Reserve a product to a work order reserve"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Return a bad request error if the work order reserve is closed, if there is insufficient quantity in the storage or if reserving the product exceeds the required quantity",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDTO.class))}
            )
    }
    )
    public ResponseEntity<Void> reserveProduct(@PathVariable int workOrderId, @RequestBody ReserveProductsRequest products) {
        reserveService.reservarProdutoDoEstoque(workOrderId, products);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
