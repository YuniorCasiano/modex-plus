package com.modex.monolith.inventory;

public class InventoryNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public InventoryNotFoundException(Long id) {
        super("No existe inventario con el id: " + id);
    }
}
