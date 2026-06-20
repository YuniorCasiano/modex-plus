// ============================================================
// InventoryService.java
// Igual logica de negocio que el original, pero el metodo
// handleOrderCreated() (que antes era un @KafkaListener)
// ahora es reserveStock(), un metodo Java normal que
// OrderService llama directamente dentro de la misma
// transaccion. Ya no se publican StockReservedEvent ni
// StockFailedEvent en un broker — se devuelve el resultado
// de inmediato como StockReservationResult.
// ============================================================

package com.modex.monolith.inventory;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class InventoryService {

    private final InventoryRepository inventoryRepository;

    @Transactional
    public InventoryResponseDTO createInventory(CreateInventoryDTO dto) {

        log.debug("Creando inventario para productId: {} talla: {}",
                dto.productId(), dto.size());

        if (inventoryRepository.existsByProductIdAndSize(
                dto.productId(), dto.size())) {
            throw new IllegalArgumentException(
                    "Ya existe inventario para productId: "
                            + dto.productId() + " talla: " + dto.size());
        }

        Inventory inventory = Inventory.builder()
                .productId(dto.productId())
                .size(dto.size())
                .quantity(dto.quantity())
                .reservedQuantity(0)
                .build();

        Inventory saved = inventoryRepository.save(inventory);
        log.info("Inventario creado: {}", saved.getId());

        return toResponseDTO(saved);
    }

    public List<InventoryResponseDTO> getInventoryByProduct(
            String productId) {

        return inventoryRepository.findByProductId(productId)
                .stream()
                .map(this::toResponseDTO)
                .toList();
    }

    // ── Reemplaza el @KafkaListener handleOrderCreated ────────
    // Antes: OrderService publicaba OrderCreatedEvent en Kafka,
    // este metodo lo escuchaba async y publicaba de vuelta
    // StockReservedEvent o StockFailedEvent.
    //
    // Ahora: OrderService llama este metodo directamente y
    // recibe el resultado de inmediato, dentro de la misma
    // transaccion (@Transactional en OrderService.createOrder
    // envuelve tambien esta llamada).
    @Transactional
    public StockReservationResult reserveStock(
            String productId, String size, Integer quantity) {

        log.info("Intentando reservar stock - productId: {} talla: {} cantidad: {}",
                productId, size, quantity);

        return inventoryRepository
                .findByProductIdAndSize(productId, size)
                .map(inventory -> processStockReservation(inventory, quantity))
                .orElseGet(() -> StockReservationResult.failed(
                        "No existe inventario para productId: "
                                + productId + " talla: " + size
                ));
    }

    private StockReservationResult processStockReservation(
            Inventory inventory, Integer quantity) {

        int available = inventory.getQuantity()
                - inventory.getReservedQuantity();

        if (available >= quantity) {
            inventory.setReservedQuantity(
                    inventory.getReservedQuantity() + quantity);
            inventoryRepository.save(inventory);

            log.info("Stock reservado: productId {} talla {}",
                    inventory.getProductId(), inventory.getSize());

            return StockReservationResult.ok();
        }

        String reason = "Stock insuficiente para productId: "
                + inventory.getProductId()
                + " talla: " + inventory.getSize()
                + ". Disponible: " + available
                + " Solicitado: " + quantity;

        log.warn(reason);

        return StockReservationResult.failed(reason);
    }

    private InventoryResponseDTO toResponseDTO(Inventory inventory) {
        return new InventoryResponseDTO(
                inventory.getId(),
                inventory.getProductId(),
                inventory.getSize(),
                inventory.getQuantity(),
                inventory.getReservedQuantity(),
                inventory.getQuantity() - inventory.getReservedQuantity(),
                inventory.getCreatedAt(),
                inventory.getUpdatedAt()
        );
    }
}