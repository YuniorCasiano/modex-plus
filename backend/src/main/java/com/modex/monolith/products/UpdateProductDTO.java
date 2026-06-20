package com.modex.monolith.products;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;

import java.math.BigDecimal;
import java.util.List;

public record UpdateProductDTO(

        String name,
        String description,

        @DecimalMin(value = "0.01", message = "El precio debe ser mayor que 0")
        BigDecimal price,

        String category,
        String gender,
        String brand,
        List<String> availableSizes,
        List<String> availableColors,
        String imageUrl,

        @Min(value = 0, message = "El stock no puede ser negativo")
        Integer stock

) {}
