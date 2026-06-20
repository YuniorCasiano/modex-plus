package com.modex.monolith.auth;

import jakarta.validation.constraints.NotBlank;

public record RefreshTokenRequestDTO(

        @NotBlank(message = "El refresh token es obligatorio")
        String refreshToken

) {}
