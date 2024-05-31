package com.workordercontrol.api.Infra.Repository;

import com.workordercontrol.api.Infra.Entity.WorkOrder;
import com.workordercontrol.api.Infra.Entity.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface WorkOrderRepository extends JpaRepository<WorkOrder, Integer> {

    List<WorkOrder> findByStatus(Status status);

    @Query(nativeQuery = true, value = "DELETE FROM workorders WHERE workorders.employee_id = :employeeId")
    void deleteByEmployeeId(@Param("employeeId") UUID employeeId);

    @Query(nativeQuery = true, value = "DELETE FROM workorders WHERE workorders.client_id = :clientId")
    void deleteByClientId(@Param("clientId") UUID clientId);

}
