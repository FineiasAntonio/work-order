package com.eletroficinagalvao.controledeservico.Infra.Repository;


import com.eletroficinagalvao.controledeservico.Infra.Entity.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, UUID> {
    List<Produto> findByProdutoLike(String name);

    Set<Produto> findByQuantidadeGreaterThan(int quantidade);

    Optional<Produto> findByProduto(String name);

}
