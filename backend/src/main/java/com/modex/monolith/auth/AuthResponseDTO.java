package com.modex.monolith.auth;

public record AuthResponseDTO(
        String accessToken,
        String refreshToken,
        String tokenType,
        Long expiresIn,
        String email,
        String fullName,
        String role
) {
    public static AuthResponseDTO of(
            String accessToken,
            String refreshToken,
            Long expiresIn,
            String email,
            String fullName,
            String role) {

        return new AuthResponseDTO(
                accessToken,
                refreshToken,
                "Bearer",
                expiresIn,
                email,
                fullName,
                role
        );
    }
}
