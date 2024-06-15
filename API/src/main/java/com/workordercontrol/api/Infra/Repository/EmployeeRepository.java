package com.workordercontrol.api.Infra.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.workordercontrol.api.Infra.Entity.Employee;

import java.util.UUID;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, UUID> {

    @Query(nativeQuery = true, value = "SELECT * FROM employees WHERE employee.email = :email")
    Employee findByEmail(@Param("email") String email);
    
}
