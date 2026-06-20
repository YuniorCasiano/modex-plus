package com.modex.monolith.auth;

import com.modex.monolith.users.UserResponseDTO;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponseDTO> register(
            @Valid @RequestBody RegisterRequestDTO dto) {

        log.info("POST /api/auth/register - Registrando: {}",
                dto.email());

        AuthResponseDTO response = authService.register(dto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(
            @Valid @RequestBody LoginRequestDTO dto) {

        log.info("POST /api/auth/login - Login para: {}",
                dto.email());

        AuthResponseDTO response = authService.login(dto);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthResponseDTO> refresh(
            @Valid @RequestBody RefreshTokenRequestDTO dto) {

        log.info("POST /api/auth/refresh - Renovando token");

        AuthResponseDTO response = authService.refreshToken(dto);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(
            @Valid @RequestBody RefreshTokenRequestDTO dto) {

        log.info("POST /api/auth/logout - Cerrando sesion");

        authService.logout(dto.refreshToken());

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserResponseDTO>> getAllUsers() {
        log.info("GET /api/auth/users - Obteniendo todos los usuarios");
        return ResponseEntity.ok(authService.getAllUsers());
    }
}
