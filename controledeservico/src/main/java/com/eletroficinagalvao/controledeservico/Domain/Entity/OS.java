package com.eletroficinagalvao.controledeservico.Domain.Entity;

import com.eletroficinagalvao.controledeservico.Exception.CustomExceptions.InternalServerErrorException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@Data
@ToString
@Entity
public class OS {

    @Transient
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String nome;
    private String telefone;
    private String endereco;
    private String cpf;
    private Date dataEntrada;
    private Date dataSaida;
    private Date dataConclusao;
    private Date dataEntrega;
    private String equipamento;
    private String numeroSerie;
    private String observacao;
    private String servico;
    private ServicoSituacao situacao;
    private SubSituacao subSituacao;
    @Column(columnDefinition = "jsonb")
    private String medias = "[]";
    private String video;
    @OneToOne
    @JoinColumn(name = "employee_id")
    private Funcionario funcionario;
    private String comentarios;
    @OneToOne
    @JoinColumn(name = "reserve_id")
    private Reserva reserva;
    private double valorTotal;

    public List<Media> getMedias(){
        try {
            return objectMapper.readValue(this.medias, new TypeReference<List<Media>>(){});
        } catch (JsonProcessingException e) {
            throw new InternalServerErrorException("Error while parsing attribute");
        }
    }

    public void setMedias(List<Media> medias){
        try {
            this.medias = objectMapper.writeValueAsString(medias);
        } catch (JsonProcessingException e) {
            throw new InternalServerErrorException("Error while parsing attribute");
        }
    }
}
