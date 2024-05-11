package com.workordercontrol.api.Controller;

import com.workordercontrol.api.Domain.Service.WorkOrderService;
import com.workordercontrol.api.Infra.DTO.OS.WorkOrderCreateRequest;
import com.workordercontrol.api.Infra.DTO.OS.WorkOrderUpdateRequest;
import com.workordercontrol.api.Infra.Entity.WorkOrder;

import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
@RequestMapping ("/ordensdeservicos")
public class OSController {

    @Autowired
    private WorkOrderService service;

    @GetMapping
    @ApiResponses()
    public ResponseEntity<List<WorkOrder>> getAll(){
        List<WorkOrder> ordens = service.getAll();
        return ResponseEntity.status(HttpStatus.OK).body(ordens);
    }

    @GetMapping ("/{id}")
    public ResponseEntity<WorkOrder> getById(@PathVariable int id){
        WorkOrder ordem = service.getById(id);
        return ResponseEntity.status(HttpStatus.OK).body(ordem);
    }

    @PostMapping
    public ResponseEntity<WorkOrder> create(@RequestBody WorkOrderCreateRequest ordemdeservico){
        WorkOrder ordemCriada = service.create(ordemdeservico);
        return ResponseEntity.status(HttpStatus.CREATED).body(ordemCriada);
    }

    @DeleteMapping ("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id){
        service.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<WorkOrder> update(@PathVariable int id, @RequestBody WorkOrderUpdateRequest os){
        System.out.println(os);
        WorkOrder ordemAtualizada = service.update(id, os);
        return ResponseEntity.status(HttpStatus.CREATED).body(ordemAtualizada);
    }

    @GetMapping("/print/{id}")
    public ModelAndView getDocDetails(@PathVariable("id") int id){
        ModelAndView mv = new ModelAndView("doc");
        WorkOrder os = service.getById(id);
        mv.addObject("os", os);
        return mv;
    }

    @PostMapping("/media/{id}")
    public ResponseEntity<Void> storageMidia(@PathVariable("id") int id, @RequestBody Media media){
        service.storageMidia(id, media);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
