package com.workordercontrol.api.Controller;

import com.workordercontrol.api.Domain.Service.EstoqueService;
import com.workordercontrol.api.Infra.DTO.Estoque.ProductRequest;
import com.workordercontrol.api.Infra.Entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping ("/estoque")
public class EstoqueController {

    @Autowired
    private EstoqueService service;

    @GetMapping
    public ResponseEntity<List<Product>> getAll(){
        return new ResponseEntity<>(service.getAll(), HttpStatus.OK);
    }

    @GetMapping ("/{id}")
    public ResponseEntity<Product> getById(@PathVariable String id){
        return new ResponseEntity<>(service.getById(UUID.fromString(id)), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody ProductRequest produtos){
            service.create(produtos);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping ("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id){
        service.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping ("/{id}")
    public ResponseEntity<Void> update(@PathVariable String id, @RequestBody Product produto){
        service.update(UUID.fromString(id), produto);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
