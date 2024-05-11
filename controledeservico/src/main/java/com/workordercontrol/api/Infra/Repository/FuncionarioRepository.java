package com.workordercontrol.api.Infra.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.workordercontrol.api.Infra.Entity.Employee;

import java.util.UUID;

@Repository
public interface FuncionarioRepository extends JpaRepository<Employee, UUID> {
    
}
