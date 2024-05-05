package com.eletroficinagalvao.controledeservico.Infra.Repository;

import com.eletroficinagalvao.controledeservico.Infra.Entity.OS;
import com.eletroficinagalvao.controledeservico.Infra.Entity.ServicoSituacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OSRepository extends JpaRepository<OS, Integer> {

    List<OS> findBySituacao(ServicoSituacao situacao);

    List<OS> findByNomeLike(String name);

}
