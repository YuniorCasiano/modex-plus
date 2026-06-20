package com.modex.monolith.products;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record ProductResponseDTO(

        String id,
        String name,
        String description,
        BigDecimal price,
        String category,
        String gender,
        String brand,
        List<String> availableSizes,
        List<String> availableColors,
        String imageUrl,
        Integer stock,
        Boolean active,
        LocalDateTime createdAt,
        LocalDateTime updatedAt

) {}
