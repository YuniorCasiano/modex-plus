package com.modex.monolith.inventory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Long> {

    Optional<Inventory> findByProductIdAndSize(String productId, String size);

    List<Inventory> findByProductId(String productId);

    boolean existsByProductIdAndSize(String productId, String size);
}
