package com.eletroficinagalvao.controledeservico.Infra.Entity;

import com.eletroficinagalvao.controledeservico.Exception.CustomExceptions.InternalServerErrorException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@Builder
@Entity
public class Reserva {

    @Transient
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private int idOS;
    @Column(columnDefinition = "jsonb")
    private String produtos_reservados = "[]";
    private boolean ativo;
    private int maoDeObra;

    public Reserva(String produtos_reservados) {
        this.produtos_reservados = produtos_reservados;
    }

    public Reserva(String produtos_reservados, boolean ativo) {
        this.produtos_reservados = produtos_reservados;
        this.ativo = ativo;
    }

    public Reserva(int idOS, String produtos_reservados, boolean ativo, int maoDeObra) {
        this.idOS = idOS;
        this.produtos_reservados = produtos_reservados;
        this.ativo = ativo;
        this.maoDeObra = maoDeObra;
    }

    public List<ProdutoReservado> getProdutos_reservados(){
        try {
            return objectMapper.readValue(this.produtos_reservados, new TypeReference<List<ProdutoReservado>>(){});
        } catch (JsonProcessingException e) {
            throw new InternalServerErrorException("Error while parsing attribute");
        }
    }

    public void setProdutos_reservados(List<ProdutoReservado> products){
        try {
            this.produtos_reservados = objectMapper.writeValueAsString(products);
        } catch (JsonProcessingException e) {
            throw new InternalServerErrorException("Error while parsing attribute");
        }
    }
}
