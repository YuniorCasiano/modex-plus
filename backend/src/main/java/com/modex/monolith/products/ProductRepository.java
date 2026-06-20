package com.modex.monolith.products;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository
        extends MongoRepository<Product, String> {

    List<Product> findByActiveTrue();

    List<Product> findByCategoryAndActiveTrue(String category);

    List<Product> findByBrandAndActiveTrue(String brand);

    List<Product> findByCategoryAndBrandAndActiveTrue(
            String category, String brand);

    @Query("{ 'available_sizes': ?0, 'active': true }")
    List<Product> findByAvailableSizesContaining(String size);

    boolean existsByNameAndActiveTrue(String name);
}
