package com.workordercontrol.api.Controller;

import com.workordercontrol.api.Domain.Service.ReservaService;
import com.workordercontrol.api.Infra.DTO.Reserva.ReservaProdutoExistenteDTO;
import com.workordercontrol.api.Infra.Entity.Reserve;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping ("/reserve")
public class ReservaController {

    @Autowired
    private ReservaService reservaService;


    @GetMapping()
    public ResponseEntity<List<Reserve>> listar() {
        return new ResponseEntity<>(reservaService.getAll(), HttpStatus.OK);
    }

    @PostMapping("/{os}")
    public ResponseEntity<Void> reservarProduto(@PathVariable int os, @RequestBody ReservaProdutoExistenteDTO produto){
        reservaService.reservarProdutoDoEstoque(os, produto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
