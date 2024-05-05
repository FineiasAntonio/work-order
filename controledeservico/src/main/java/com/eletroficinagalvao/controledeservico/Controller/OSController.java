package com.eletroficinagalvao.controledeservico.Controller;

import com.eletroficinagalvao.controledeservico.Infra.DTO.OS.CreateOSRequestDTO;
import com.eletroficinagalvao.controledeservico.Infra.DTO.OS.UpdateOSRequestDTO;
import com.eletroficinagalvao.controledeservico.Infra.Entity.Media;
import com.eletroficinagalvao.controledeservico.Infra.Entity.OS;
import com.eletroficinagalvao.controledeservico.Domain.Service.OSService;

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
    private OSService service;

    @GetMapping
    @ApiResponses()
    public ResponseEntity<List<OS>> getAll(){
        List<OS> ordens = service.getAll();
        return ResponseEntity.status(HttpStatus.OK).body(ordens);
    }

    @GetMapping ("/{id}")
    public ResponseEntity<OS> getById(@PathVariable int id){
        OS ordem = service.getById(id);
        return ResponseEntity.status(HttpStatus.OK).body(ordem);
    }

    @PostMapping
    public ResponseEntity<OS> create(@RequestBody CreateOSRequestDTO ordemdeservico){
        OS ordemCriada = service.create(ordemdeservico);
        return ResponseEntity.status(HttpStatus.CREATED).body(ordemCriada);
    }

    @DeleteMapping ("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id){
        service.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<OS> update(@PathVariable int id, @RequestBody UpdateOSRequestDTO os){
        System.out.println(os);
        OS ordemAtualizada = service.update(id, os);
        return ResponseEntity.status(HttpStatus.CREATED).body(ordemAtualizada);
    }

    @GetMapping("/print/{id}")
    public ModelAndView getDocDetails(@PathVariable("id") int id){
        ModelAndView mv = new ModelAndView("doc");
        OS os = service.getById(id);
        mv.addObject("os", os);
        return mv;
    }

    @PostMapping("/media/{id}")
    public ResponseEntity<Void> storageMidia(@PathVariable("id") int id, @RequestBody Media media){
        service.storageMidia(id, media);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
