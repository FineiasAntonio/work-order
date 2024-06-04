package com.workordercontrol.api.Controller;

import com.workordercontrol.api.Domain.Service.StorageService;
import com.workordercontrol.api.Exception.ExceptionDTO;
import com.workordercontrol.api.Infra.DTO.Estoque.ProductRequest;
import com.workordercontrol.api.Infra.Entity.Product;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping ("/storage")
public class StorageController {

    @Autowired
    private StorageService service;

    @GetMapping
    @ApiResponses(
            @ApiResponse(
                    responseCode = "200",
                    description = "Return all products in storage",
                    content = {@Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Product.class)))}
            )
    )
    public ResponseEntity<List<Product>> getAll(){
        return ResponseEntity.status(HttpStatus.OK).body(service.getAll());
    }

    @GetMapping ("/{productId}")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Return a specific product by id",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Product.class))}

            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Return not found error if doesn't have any product with the ID passed by the user",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDTO.class))}
            )
    }
    )
    public ResponseEntity<Product> getById(@PathVariable UUID productId){
        return ResponseEntity.status(HttpStatus.OK).body(service.getById(productId));
    }

    @PostMapping
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Register a new product in storage and return its complete data",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Product.class))}
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Return bad request error if the request has some invalid field",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDTO.class))}
            )
    }
    )
    public ResponseEntity<Product> create(@RequestBody ProductRequest productRequest){
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(productRequest));
    }

    @DeleteMapping ("/{productId}")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description = "Delete a specific product by id"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Return not found error if doesn't have any product with the ID passed by the user",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDTO.class))}
            )}
    )
    public ResponseEntity<Void> delete(@PathVariable UUID productId){
        service.delete(productId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PutMapping ("/{productId}")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Update an existing product and return its complete data",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Product.class))}
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Return bad request error if request has some invalid field",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDTO.class))}
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Return not found error if doesn't have any product with the ID passed by the user",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDTO.class))}
            )
    }
    )
    public ResponseEntity<Product> update(@PathVariable UUID productId, @RequestBody Product product){
        return ResponseEntity.status(HttpStatus.CREATED).body(service.update(productId, product));
    }
}
