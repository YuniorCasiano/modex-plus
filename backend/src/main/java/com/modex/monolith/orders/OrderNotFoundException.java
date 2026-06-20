package com.modex.monolith.orders;

public class OrderNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public OrderNotFoundException(Long id) {
        super("No existe pedido con el id: " + id);
    }
}
