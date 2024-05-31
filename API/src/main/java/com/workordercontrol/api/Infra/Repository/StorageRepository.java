package com.workordercontrol.api.Infra.Repository;


import com.workordercontrol.api.Infra.Entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Repository
public interface StorageRepository extends JpaRepository<Product, UUID> {
    @Query(nativeQuery = true, value = "SELECT * FROM products WHERE products.quantity = :quantity")
    Set<Product> findByQuantityGreaterThan(@Param("quantity") int quantity);

    Optional<Product> findByName(String name);

}
