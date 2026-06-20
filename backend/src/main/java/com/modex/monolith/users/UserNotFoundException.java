package com.modex.monolith.users;

public class UserNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public UserNotFoundException(String id) {
        super("No existe usuario con el id: " + id);
    }
}
