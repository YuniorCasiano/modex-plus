package com.modex.monolith.products;

public class ProductMapper {

    private ProductMapper() {}

    public static ProductResponseDTO toResponseDTO(Product product) {

        if (product == null) {
            return null;
        }

        return new ProductResponseDTO(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getCategory(),
                product.getGender(),
                product.getBrand(),
                product.getAvailableSizes(),
                product.getAvailableColors(),
                product.getImageUrl(),
                product.getStock(),
                product.getActive(),
                product.getCreatedAt(),
                product.getUpdatedAt()
        );
    }

    public static Product toModel(CreateProductDTO dto) {

        if (dto == null) {
            return null;
        }

        return Product.builder()
                .name(dto.name())
                .description(dto.description())
                .price(dto.price())
                .category(dto.category())
                .gender(dto.gender())
                .brand(dto.brand())
                .availableSizes(dto.availableSizes())
                .availableColors(dto.availableColors())
                .imageUrl(dto.imageUrl())
                .stock(dto.stock())
                .active(true)
                .build();
    }

    public static Product applyUpdates(Product product,
                                       UpdateProductDTO dto) {

        if (dto == null) {
            return product;
        }

        if (dto.name() != null && !dto.name().isBlank()) {
            product.setName(dto.name());
        }

        if (dto.description() != null && !dto.description().isBlank()) {
            product.setDescription(dto.description());
        }

        if (dto.price() != null) {
            product.setPrice(dto.price());
        }

        if (dto.category() != null && !dto.category().isBlank()) {
            product.setCategory(dto.category());
        }

        if (dto.gender() != null && !dto.gender().isBlank()) {
            product.setGender(dto.gender());
        }

        if (dto.brand() != null && !dto.brand().isBlank()) {
            product.setBrand(dto.brand());
        }

        if (dto.availableSizes() != null) {
            product.setAvailableSizes(dto.availableSizes());
        }

        if (dto.availableColors() != null) {
            product.setAvailableColors(dto.availableColors());
        }

        if (dto.imageUrl() != null) {
            product.setImageUrl(dto.imageUrl());
        }

        if (dto.stock() != null) {
            product.setStock(dto.stock());
        }

        return product;
    }
}
