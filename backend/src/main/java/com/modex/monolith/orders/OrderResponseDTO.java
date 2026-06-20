package com.modex.monolith.orders;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record OrderResponseDTO(

        Long id,
        String userId,
        String productId,
        String productName,
        String size,
        String color,
        Integer quantity,
        BigDecimal unitPrice,
        BigDecimal totalPrice,
        OrderStatus status,
        String cancellationReason,
        String shippingAddress,
        LocalDateTime createdAt,
        LocalDateTime updatedAt

) {}
