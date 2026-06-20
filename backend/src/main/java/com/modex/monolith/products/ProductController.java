package com.modex.monolith.products;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping
    public ResponseEntity<List<ProductResponseDTO>> getAllProducts() {
        log.info("GET /api/products");
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> getProductById(
            @PathVariable String id) {
        log.info("GET /api/products/{}", id);
        return ResponseEntity.ok(productService.getProductById(id));
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<List<ProductResponseDTO>> getByCategory(
            @PathVariable String category) {
        log.info("GET /api/products/category/{}", category);
        return ResponseEntity.ok(
                productService.getProductsByCategory(category));
    }

    @GetMapping("/size/{size}")
    public ResponseEntity<List<ProductResponseDTO>> getBySize(
            @PathVariable String size) {
        log.info("GET /api/products/size/{}", size);
        return ResponseEntity.ok(
                productService.getProductsBySize(size));
    }

    @PostMapping
    public ResponseEntity<ProductResponseDTO> createProduct(
            @Valid @RequestBody CreateProductDTO dto) {
        log.info("POST /api/products - Creando: {}", dto.name());
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(productService.createProduct(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> updateProduct(
            @PathVariable String id,
            @Valid @RequestBody UpdateProductDTO dto) {
        log.info("PUT /api/products/{}", id);
        return ResponseEntity.ok(
                productService.updateProduct(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deactivateProduct(
            @PathVariable String id) {
        log.info("DELETE /api/products/{}", id);
        productService.deactivateProduct(id);
        return ResponseEntity.noContent().build();
    }
}
