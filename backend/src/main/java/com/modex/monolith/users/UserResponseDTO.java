// ============================================================
// UserResponseDTO.java - Lo que se devuelve al frontend
// Nunca incluye el password, ni de auth ni de user-service.
// ============================================================

package com.modex.monolith.users;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDTO {

    private String id;
    private String fullName;
    private String email;
    private String phoneNumber;
    private String shippingAddress;
    private String city;
    private String country;
    private String role;
    private Boolean active;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
