package com.modex.monolith.orders;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record CreateOrderDTO(

        @NotBlank(message = "El ID del producto es obligatorio")
        String productId,

        @NotBlank(message = "El nombre del producto es obligatorio")
        String productName,

        @NotBlank(message = "La talla es obligatoria")
        String size,

        String color,

        @NotNull(message = "La cantidad es obligatoria")
        @Min(value = 1, message = "La cantidad debe ser minimo 1")
        Integer quantity,

        @NotNull(message = "El precio unitario es obligatorio")
        BigDecimal unitPrice,

        String shippingAddress

) {}
