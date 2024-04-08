package com.eletroficinagalvao.controledeservico.Controller;

import com.eletroficinagalvao.controledeservico.Domain.DTO.OS.CreateOSRequestDTO;
import com.eletroficinagalvao.controledeservico.Domain.DTO.OS.UpdateOSRequestDTO;
import com.eletroficinagalvao.controledeservico.Domain.Entity.Midia;
import com.eletroficinagalvao.controledeservico.Domain.Entity.OS;
import com.eletroficinagalvao.controledeservico.Exception.CustomExceptions.ExceptionDTO;
import com.eletroficinagalvao.controledeservico.Service.OSService;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
@RequestMapping ("/ordensdeservicos")
public class OSController {

    @Autowired
    private OSService service;

    @GetMapping
    @ApiResponses(
            @ApiResponse(
                    responseCode = "200",
                    description = "Return all work order",
                    content = {@Content(mediaType = "application/json")}
            )
    )
    public ResponseEntity<List<OS>> getAll(){
        List<OS> ordens = service.getAll();
        return ResponseEntity.status(HttpStatus.OK).body(ordens);
    }

    @GetMapping ("/{id}")
    @ApiResponses( value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Return a specific work order",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = OS.class))}

            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Return 404 code if doesn't have any work order with the ID passed by the user",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDTO.class))}
            )
    }
    )
    public ResponseEntity<OS> getById(@PathVariable int id){
        OS ordem = service.getById(id);
        return ResponseEntity.status(HttpStatus.OK).body(ordem);
    }

    @PostMapping
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Register a new work order and return its complete data",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = OS.class))}
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Return 400 code if request has some invalid field",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDTO.class))}
            )
    }
    )
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

    @PostMapping("/midia/{id}")
    public ResponseEntity<Void> storageMidia(@PathVariable("id") int id, @RequestBody Midia midia){
        service.storageMidia(id, midia);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
