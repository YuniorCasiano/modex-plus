package com.modex.monolith.products;

public class ProductNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public ProductNotFoundException(String id) {
        super("No existe producto con el id: " + id);
    }
}
