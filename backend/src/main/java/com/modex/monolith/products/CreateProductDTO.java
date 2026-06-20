package com.modex.monolith.products;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.List;

public record CreateProductDTO(

        @NotBlank(message = "El nombre es obligatorio")
        String name,

        @NotBlank(message = "La descripcion es obligatoria")
        String description,

        @NotNull(message = "El precio es obligatorio")
        @DecimalMin(value = "0.01", message = "El precio debe ser mayor que 0")
        BigDecimal price,

        @NotBlank(message = "La categoria es obligatoria")
        String category,

        String gender,

        @NotBlank(message = "La marca es obligatoria")
        String brand,

        @NotNull(message = "Las tallas son obligatorias")
        List<String> availableSizes,

        @NotNull(message = "Los colores son obligatorios")
        List<String> availableColors,

        String imageUrl,

        @NotNull(message = "El stock es obligatorio")
        @Min(value = 0, message = "El stock no puede ser negativo")
        Integer stock

) {}
