// ============================================================
// ProductService.java
// Igual que el original, pero el cache ahora es en memoria
// (ConcurrentMapCacheManager configurado en CacheConfig)
// en vez de Redis. Las anotaciones @Cacheable y @CacheEvict
// funcionan exactamente igual.
// ============================================================

package com.modex.monolith.products;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    @Cacheable(value = "products", key = "'all'")
    public List<ProductResponseDTO> getAllProducts() {
        log.debug("Consultando todos los productos desde MongoDB (sin cache)");
        return productRepository.findByActiveTrue()
                .stream()
                .map(ProductMapper::toResponseDTO)
                .toList();
    }

    @Cacheable(value = "products", key = "#id")
    public ProductResponseDTO getProductById(String id) {
        log.debug("Consultando producto {} desde MongoDB (sin cache)", id);
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
        return ProductMapper.toResponseDTO(product);
    }

    public List<ProductResponseDTO> getProductsByCategory(String category) {
        return productRepository.findByCategoryAndActiveTrue(category)
                .stream()
                .map(ProductMapper::toResponseDTO)
                .toList();
    }

    public List<ProductResponseDTO> getProductsBySize(String size) {
        return productRepository.findByAvailableSizesContaining(size)
                .stream()
                .map(ProductMapper::toResponseDTO)
                .toList();
    }

    @CacheEvict(value = "products", allEntries = true)
    public ProductResponseDTO createProduct(CreateProductDTO dto) {

        if (productRepository.existsByNameAndActiveTrue(dto.name())) {
            throw new IllegalArgumentException(
                    "Ya existe un producto activo con el nombre: " + dto.name()
            );
        }

        Product product = ProductMapper.toModel(dto);
        Product saved = productRepository.save(product);

        log.info("Producto creado: {}", saved.getName());

        return ProductMapper.toResponseDTO(saved);
    }

    @CacheEvict(value = "products", allEntries = true)
    public ProductResponseDTO updateProduct(String id, UpdateProductDTO dto) {

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));

        Product updated = ProductMapper.applyUpdates(product, dto);
        Product saved = productRepository.save(updated);

        log.info("Producto actualizado: {}", saved.getName());

        return ProductMapper.toResponseDTO(saved);
    }

    @CacheEvict(value = "products", allEntries = true)
    public void deactivateProduct(String id) {

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));

        product.setActive(false);
        productRepository.save(product);

        log.info("Producto desactivado: {}", product.getName());
    }
}
