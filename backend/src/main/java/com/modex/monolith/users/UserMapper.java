// ============================================================
// UserMapper.java - Convierte entre User y los DTOs
// Fusion del mapper de auth-service y user-service.
// ============================================================

package com.modex.monolith.users;

import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserResponseDTO toResponseDTO(User user) {
        return UserResponseDTO.builder()
                .id(user.getId())
                .fullName(user.getFullName())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .shippingAddress(user.getShippingAddress())
                .city(user.getCity())
                .country(user.getCountry())
                .role(user.getRole())
                .active(user.getActive())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build();
    }

    // Aplica los cambios de UpdateUserDTO sobre un User existente.
    // Solo actualiza los campos que vienen no-nulos.
    public void applyUpdate(User user, UpdateUserDTO dto) {
        if (dto.getFullName() != null) {
            user.setFullName(dto.getFullName());
        }
        if (dto.getPhoneNumber() != null) {
            user.setPhoneNumber(dto.getPhoneNumber());
        }
        if (dto.getShippingAddress() != null) {
            user.setShippingAddress(dto.getShippingAddress());
        }
        if (dto.getCity() != null) {
            user.setCity(dto.getCity());
        }
        if (dto.getCountry() != null) {
            user.setCountry(dto.getCountry());
        }
    }
}
