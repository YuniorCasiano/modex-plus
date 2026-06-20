// ============================================================
// OrderService.java
//
// CAMBIO PRINCIPAL respecto al microservicio original:
// Antes, createOrder() guardaba el pedido en PENDING y
// publicaba OrderCreatedEvent en Kafka. El Inventory Service
// lo escuchaba de forma asincrona y respondia con
// StockReservedEvent o StockFailedEvent, que el Order Service
// escuchaba con @KafkaListener para confirmar o cancelar.
//
// Ahora, dentro del mismo metodo createOrder() y la misma
// transaccion @Transactional, se llama directamente a
// inventoryService.reserveStock(...). El resultado se conoce
// de inmediato — no hace falta esperar un evento async ni
// los metodos handleStockReserved/handleStockFailed.
//
// El pedido se crea ya CONFIRMED o CANCELLED segun el
// resultado de la reserva de stock, en la misma llamada HTTP.
// El patron Saga (reservar stock como parte de crear el
// pedido) se mantiene — solo cambia el mecanismo de
// comunicacion entre los dos dominios.
// ============================================================

package com.modex.monolith.orders;

import com.modex.monolith.inventory.InventoryService;
import com.modex.monolith.inventory.StockReservationResult;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final InventoryService inventoryService;

    @Transactional
    public OrderResponseDTO createOrder(CreateOrderDTO dto,
                                        String userId) {

        log.debug("Creando pedido para usuario: {}", userId);

        BigDecimal totalPrice = dto.unitPrice()
                .multiply(BigDecimal.valueOf(dto.quantity()));

        Order order = Order.builder()
                .userId(userId)
                .productId(dto.productId())
                .productName(dto.productName())
                .size(dto.size())
                .color(dto.color())
                .quantity(dto.quantity())
                .unitPrice(dto.unitPrice())
                .totalPrice(totalPrice)
                .status(OrderStatus.PENDING)
                .shippingAddress(dto.shippingAddress())
                .build();

        Order saved = orderRepository.save(order);

        // ── Reserva de stock directa (antes era via Kafka) ────
        StockReservationResult result = inventoryService.reserveStock(
                saved.getProductId(),
                saved.getSize(),
                saved.getQuantity()
        );

        if (result.success()) {
            saved.setStatus(OrderStatus.CONFIRMED);
            log.info("Pedido {} CONFIRMADO", saved.getId());
        } else {
            saved.setStatus(OrderStatus.CANCELLED);
            saved.setCancellationReason(result.reason());
            log.warn("Pedido {} CANCELADO: {}", saved.getId(), result.reason());
        }

        Order finalOrder = orderRepository.save(saved);

        return toResponseDTO(finalOrder);
    }

    public OrderResponseDTO getOrderById(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException(id));
        return toResponseDTO(order);
    }

    public List<OrderResponseDTO> getOrdersByUser(String userId) {
        return orderRepository
                .findByUserIdOrderByCreatedAtDesc(userId)
                .stream()
                .map(this::toResponseDTO)
                .toList();
    }

    @Transactional
    public OrderResponseDTO cancelOrder(Long id, String userId) {

        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException(id));

        if (!order.getUserId().equals(userId)) {
            throw new IllegalArgumentException(
                    "No tienes permiso para cancelar este pedido");
        }

        if (order.getStatus() != OrderStatus.PENDING
                && order.getStatus() != OrderStatus.CONFIRMED) {
            throw new IllegalArgumentException(
                    "Este pedido ya no se puede cancelar");
        }

        order.setStatus(OrderStatus.CANCELLED);
        order.setCancellationReason("Cancelado por el usuario");
        Order saved = orderRepository.save(order);

        log.info("Pedido {} cancelado por el usuario", id);

        return toResponseDTO(saved);
    }

    public List<OrderResponseDTO> getAllOrders() {
        return orderRepository.findAll()
                .stream()
                .map(this::toResponseDTO)
                .toList();
    }

    public OrderResponseDTO updateOrderStatus(Long id, String status) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException(id));
        order.setStatus(OrderStatus.valueOf(status));
        return toResponseDTO(orderRepository.save(order));
    }

    private OrderResponseDTO toResponseDTO(Order order) {
        return new OrderResponseDTO(
                order.getId(),
                order.getUserId(),
                order.getProductId(),
                order.getProductName(),
                order.getSize(),
                order.getColor(),
                order.getQuantity(),
                order.getUnitPrice(),
                order.getTotalPrice(),
                order.getStatus(),
                order.getCancellationReason(),
                order.getShippingAddress(),
                order.getCreatedAt(),
                order.getUpdatedAt()
        );
    }
}
