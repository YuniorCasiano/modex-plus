package com.modex.monolith.orders;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<OrderResponseDTO> createOrder(
            @Valid @RequestBody CreateOrderDTO dto,
            @AuthenticationPrincipal UserDetails userDetails) {

        String userId = userDetails.getUsername();

        log.info("POST /api/orders - Usuario: {}", userId);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(orderService.createOrder(dto, userId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponseDTO> getOrderById(
            @PathVariable Long id) {
        log.info("GET /api/orders/{}", id);
        return ResponseEntity.ok(orderService.getOrderById(id));
    }

    @GetMapping("/my-orders")
    public ResponseEntity<List<OrderResponseDTO>> getMyOrders(
            @AuthenticationPrincipal UserDetails userDetails) {

        String userId = userDetails.getUsername();
        log.info("GET /api/orders/my-orders - Usuario: {}", userId);
        return ResponseEntity.ok(
                orderService.getOrdersByUser(userId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<OrderResponseDTO> cancelOrder(
            @PathVariable Long id,
            @AuthenticationPrincipal UserDetails userDetails) {

        String userId = userDetails.getUsername();
        log.info("DELETE /api/orders/{} - Usuario: {}", id, userId);
        return ResponseEntity.ok(
                orderService.cancelOrder(id, userId));
    }

    @GetMapping
    public ResponseEntity<List<OrderResponseDTO>> getAllOrders() {
        log.info("GET /api/orders - Admin obteniendo todos los pedidos");
        return ResponseEntity.ok(orderService.getAllOrders());
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<OrderResponseDTO> updateStatus(
            @PathVariable Long id,
            @RequestParam String status) {
        log.info("PUT /api/orders/{}/status - Nuevo estado: {}", id, status);
        return ResponseEntity.ok(orderService.updateOrderStatus(id, status));
    }
}
