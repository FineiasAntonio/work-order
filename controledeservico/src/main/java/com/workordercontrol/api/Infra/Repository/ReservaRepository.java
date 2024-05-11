package com.workordercontrol.api.Infra.Repository;

import com.workordercontrol.api.Infra.Entity.Reserve;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ReservaRepository extends JpaRepository<Reserve, UUID> {

    @Query(nativeQuery = true, value = "SELECT * FROM reserves WHERE reserves.work_order_id = :workOrderId")
    Reserve findByWorkOrderId(@Param("workOrderId") int workOrderId);

    @Query(nativeQuery = true, value = "DELETE FROM reserves WHERE reserves.work_order_id = :workOrderId")
    void deleteByWorkOrderId(@Param("workOrderId") int workOrderId);

    List<Reserve> findByAtivo(boolean ativo);

}
