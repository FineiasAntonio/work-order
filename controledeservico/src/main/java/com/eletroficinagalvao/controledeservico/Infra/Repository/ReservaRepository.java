package com.eletroficinagalvao.controledeservico.Infra.Repository;

import com.eletroficinagalvao.controledeservico.Infra.Entity.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ReservaRepository extends JpaRepository<Reserva, UUID> {

    public Reserva findByIdOS(int idOS);

    public List<Reserva> findByAtivo(boolean ativo);

}
