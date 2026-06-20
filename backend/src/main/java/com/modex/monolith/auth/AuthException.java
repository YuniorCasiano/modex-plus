// ============================================================
// AuthException.java - Excepcion de autenticacion
// Se lanza cuando algo falla en el proceso de autenticacion.
// El GlobalExceptionHandler la convierte en HTTP 401.
// ============================================================

package com.modex.monolith.auth;

public class AuthException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public AuthException(String message) {
        super(message);
    }
}
