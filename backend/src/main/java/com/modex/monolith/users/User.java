// ============================================================
// User.java - Model de usuario unificado
// Fusion del User de auth-service (tenia: email, password,
// fullName, role, active) y el User de user-service (tenia:
// fullName, email, password, phoneNumber, shippingAddress,
// city, country, active) — ambos apuntaban a la misma
// coleccion "users" en MongoDB pero en bases de datos
// distintas, lo que causaba el bug de "usuarios en 0".
//
// Ahora es un solo modelo con todos los campos de ambos.
// ============================================================

package com.modex.monolith.users;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.index.Indexed;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Document(collection = "users")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    private String id;

    @Field("full_name")
    private String fullName;

    @Indexed(unique = true)
    private String email;

    @Field("password")
    private String password;

    @Field("phone_number")
    private String phoneNumber;

    @Field("shipping_address")
    private String shippingAddress;

    @Field("city")
    private String city;

    @Field("country")
    private String country;

    // Rol del usuario - ADMIN o CLIENTE
    // Se guarda en MongoDB y se incluye en el JWT
    @Builder.Default
    @Field("role")
    private String role = "CLIENTE";

    @Builder.Default
    @Field("active")
    private Boolean active = true;

    @CreatedDate
    @Field("created_at")
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Field("updated_at")
    private LocalDateTime updatedAt;
}
