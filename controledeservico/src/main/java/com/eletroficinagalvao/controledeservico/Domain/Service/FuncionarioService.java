package com.eletroficinagalvao.controledeservico.Domain.Service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eletroficinagalvao.controledeservico.Infra.Entity.Funcionario;
import com.eletroficinagalvao.controledeservico.Exception.CustomExceptions.InternalServerErrorException;
import com.eletroficinagalvao.controledeservico.Exception.CustomExceptions.NotFoundException;
import com.eletroficinagalvao.controledeservico.Infra.Repository.FuncionarioRepository;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
@Qualifier ("FuncionarioService")
public class FuncionarioService {

    @Autowired
    private FuncionarioRepository repository;

    public List<Funcionario> getAll() {
        return repository.findAll();
    }

    public Funcionario getById(UUID id) {
        return repository.findById(id).orElseThrow(() -> new NotFoundException("Funcionário não encontrado"));
    }

    public List<Funcionario> getByLikeThisName(String name){
        return null;
    }

    @Transactional
    public Funcionario create(Funcionario t) {
        try {
            Funcionario funcionarioCriado = repository.save(t);
            log.info("Funcionário registrado: " + t.getNome());
            return funcionarioCriado;
        } catch (Exception e){
            throw new InternalServerErrorException("Funcionário não registrado" + e.getMessage());
        }
    }

    public void update(String id, Funcionario t) {
        repository.deleteById(t.getId());
        repository.save(t);
    }

    public void delete(UUID id) {
        repository.deleteById(id);
    }

    
}
