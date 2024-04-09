package com.eletroficinagalvao.controledeservico.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.eletroficinagalvao.controledeservico.Domain.Entity.Funcionario;

import java.util.UUID;

@Repository
public interface FuncionarioRepository extends JpaRepository<Funcionario, UUID> {
    
}
