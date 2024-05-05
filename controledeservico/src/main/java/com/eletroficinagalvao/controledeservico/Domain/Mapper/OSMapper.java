package com.eletroficinagalvao.controledeservico.Domain.Mapper;

import com.eletroficinagalvao.controledeservico.Infra.DTO.OS.CreateOSRequestDTO;
import com.eletroficinagalvao.controledeservico.Infra.DTO.OS.UpdateOSRequestDTO;
import com.eletroficinagalvao.controledeservico.Infra.Entity.OS;
import com.eletroficinagalvao.controledeservico.Infra.Entity.Reserva;
import com.eletroficinagalvao.controledeservico.Infra.Entity.ServicoSituacao;
import com.eletroficinagalvao.controledeservico.Infra.Entity.SubSituacao;
import com.eletroficinagalvao.controledeservico.Exception.CustomExceptions.BadRequestException;
import com.eletroficinagalvao.controledeservico.Exception.CustomExceptions.NotFoundException;
import com.eletroficinagalvao.controledeservico.Infra.Repository.FuncionarioRepository;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.time.LocalDate;

@Component
@Log4j2
public class OSMapper {
    @Autowired
    private ReservaMapper reservaMapper;
    @Autowired
    private FuncionarioRepository funcionarioRepository;


    public OS map(CreateOSRequestDTO dto) {
        OS ordemdeservico = new OS();

        if (!isValid(dto)) {
            throw new BadRequestException("Invalid work order");
        }

        ordemdeservico.setReserva(reservaMapper.criarReserva(
                dto.reserva(),
                ordemdeservico.getId()
        ));

        ordemdeservico.setNome(dto.nome());
        ordemdeservico.setCpf(dto.cpf());
        ordemdeservico.setEndereco(dto.endereco());
        ordemdeservico.setTelefone(dto.telefone());
        ordemdeservico.setEquipamento(dto.equipamento());
        ordemdeservico.setNumeroSerie(dto.numeroSerie());
        ordemdeservico.setServico(dto.servico());
        ordemdeservico.setObservacao(dto.observacao());
        ordemdeservico.setObservacao(dto.comentarios());
        ordemdeservico.setDataSaida(Date.valueOf(dto.dataSaida()));
        ordemdeservico.setFuncionario(funcionarioRepository.findById(dto.funcionarioId()).orElseThrow(() -> new NotFoundException("Employee not found")));
        ordemdeservico.setDataEntrada(Date.valueOf(LocalDate.now()));

        if (ordemdeservico.getReserva().isAtivo()) {
            ordemdeservico.setSituacao(ServicoSituacao.AGUARDANDO_PECA);
            ordemdeservico.setValorTotal(atualizarValorOS(ordemdeservico.getReserva()));
        } else {
            ordemdeservico.setSituacao(ServicoSituacao.EM_ANDAMENTO);
            ordemdeservico.setSubSituacao(SubSituacao.AGUARDANDO_ORCAMENTO);
        }

        return ordemdeservico;
    }

    public OS updateMap(OS ordemdeservico, UpdateOSRequestDTO dto) {

        if (!isValid(dto)) {
            throw new BadRequestException("Invalid work order");
        }

        ordemdeservico.setNome(dto.nome());
        ordemdeservico.setCpf(dto.cpf());
        ordemdeservico.setEndereco(dto.endereco());
        ordemdeservico.setTelefone(dto.telefone());
        ordemdeservico.setEquipamento(dto.equipamento());
        ordemdeservico.setNumeroSerie(dto.numeroSerie());
        ordemdeservico.setServico(dto.servico());
        ordemdeservico.setObservacao(dto.observacao());
        ordemdeservico.setComentarios(dto.comentarios());
        ordemdeservico.setDataSaida(Date.valueOf(dto.dataSaida().toLocalDate()));
        ordemdeservico.setSubSituacao(dto.subSituacao());
        ordemdeservico.setFuncionario(funcionarioRepository.findById(dto.funcionarioId()).orElseThrow(() -> new NotFoundException("Employee not found")));

        ordemdeservico.setReserva(reservaMapper.atualizarReserva(
                ordemdeservico.getReserva(),
                dto.reserva()
        ));

        if (dto.concluido()) {
            ordemdeservico.setSituacao(ServicoSituacao.CONCLUIDO);
            ordemdeservico.setDataConclusao(Date.valueOf(LocalDate.now()));
            if (dto.subSituacao() == SubSituacao.ENTREGUE){
                ordemdeservico.setDataEntrega(Date.valueOf(LocalDate.now()));
            }
        } else {
            if (ordemdeservico.getReserva().isAtivo()) {
                ordemdeservico.setSituacao(ServicoSituacao.AGUARDANDO_PECA);
                ordemdeservico.setValorTotal(atualizarValorOS(ordemdeservico.getReserva()));
            } else {
                ordemdeservico.setSituacao(ServicoSituacao.EM_ANDAMENTO);
            }
            ordemdeservico.setDataConclusao(null);
            ordemdeservico.setDataEntrega(null);
        }

        return ordemdeservico;
    }

    private static boolean isValid(CreateOSRequestDTO dto) {
        return dto != null &&
                !dto.nome().trim().isEmpty() &&
                !dto.equipamento().trim().isEmpty();
    }

    private static boolean isValid(UpdateOSRequestDTO dto) {
        return dto != null &&
                !dto.nome().trim().isEmpty() &&
                !dto.equipamento().trim().isEmpty();
    }

    private double atualizarValorOS(Reserva reserva){
        return reserva.getProdutos_reservados()
                .stream()
                .mapToDouble(x -> x.getPrecoUnitario() * x.getQuantidadeNescessaria())
                .reduce(0, (x, y) -> x + y) + reserva.getMaoDeObra();
    }
}
