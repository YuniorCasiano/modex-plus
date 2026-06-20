package com.modex.monolith.inventory;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;

    @PostMapping
    public ResponseEntity<InventoryResponseDTO> createInventory(
            @Valid @RequestBody CreateInventoryDTO dto) {
        log.info("POST /api/inventory - productId: {}", dto.productId());
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(inventoryService.createInventory(dto));
    }

    @GetMapping("/{productId}")
    public ResponseEntity<List<InventoryResponseDTO>> getInventoryByProduct(
            @PathVariable String productId) {
        log.info("GET /api/inventory/{}", productId);
        return ResponseEntity.ok(
                inventoryService.getInventoryByProduct(productId));
    }
}
