package com.modex.monolith.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterRequestDTO(

        @NotBlank(message = "El nombre es obligatorio")
        @Size(min = 2, max = 100, message = "El nombre debe tener entre 2 y 100 caracteres")
        String fullName,

        @NotBlank(message = "El email es obligatorio")
        @Email(message = "El formato del email no es valido")
        String email,

        @NotBlank(message = "La password es obligatoria")
        @Size(min = 8, message = "La password debe tener minimo 8 caracteres")
        String password,

        String phoneNumber,

        String shippingAddress,

        String city,

        String country

) {}
