// ============================================================
// UserRepository.java - Repositorio de usuarios unificado
// Combina los queries que antes estaban repartidos entre
// auth-service (findByEmailAndActiveTrue, existsByEmail) y
// user-service (findByEmail, findById, findByActiveTrue).
// ============================================================

package com.modex.monolith.users;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<User, String> {

    // Busca un usuario activo por email - usado en login
    Optional<User> findByEmailAndActiveTrue(String email);

    // Busca un usuario por email (activo o no) - usado por
    // el JwtAuthenticationFilter para verificar existencia
    Optional<User> findByEmail(String email);

    // Verifica si existe un usuario con ese email - usado
    // en register para evitar duplicados
    boolean existsByEmail(String email);

    // Lista todos los usuarios activos
    List<User> findByActiveTrue();
}
