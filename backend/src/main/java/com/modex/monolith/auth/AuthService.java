package com.modex.monolith.auth;

import com.modex.monolith.security.JwtUtil;
import com.modex.monolith.users.User;
import com.modex.monolith.users.UserRepository;
import com.modex.monolith.users.UserResponseDTO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final RefreshTokenService refreshTokenService;
    private final JwtUtil jwtUtil;

    private final BCryptPasswordEncoder passwordEncoder =
            new BCryptPasswordEncoder(12);

    @Transactional
    public AuthResponseDTO register(RegisterRequestDTO dto) {

        log.debug("Registrando usuario: {}", dto.email());

        if (userRepository.existsByEmail(dto.email())) {
            log.warn("Intento de registro con email duplicado: {}",
                    dto.email());
            throw new AuthException(
                    "Ya existe una cuenta con el email: " + dto.email()
            );
        }

        String encryptedPassword = passwordEncoder.encode(dto.password());

        User user = User.builder()
                .fullName(dto.fullName())
                .email(dto.email())
                .password(encryptedPassword)
                .phoneNumber(dto.phoneNumber())
                .shippingAddress(dto.shippingAddress())
                .city(dto.city())
                .country(dto.country())
                .role("CLIENTE")
                .active(true)
                .build();

        User savedUser = userRepository.save(user);

        log.info("Usuario registrado exitosamente: {}", dto.email());

        return generateTokens(
                savedUser.getEmail(),
                savedUser.getFullName(),
                savedUser.getRole()
        );
    }

    public AuthResponseDTO login(LoginRequestDTO dto) {

        log.debug("Intento de login para: {}", dto.email());

        User user = userRepository.findByEmailAndActiveTrue(dto.email())
                .orElseThrow(() -> {
                    log.warn("Login fallido - usuario no encontrado: {}",
                            dto.email());
                    return new AuthException("Credenciales incorrectas");
                });

        boolean passwordCorrecta = passwordEncoder.matches(
                dto.password(),
                user.getPassword()
        );

        if (!passwordCorrecta) {
            log.warn("Login fallido - password incorrecta para: {}",
                    dto.email());
            throw new AuthException("Credenciales incorrectas");
        }

        log.info("Login exitoso para: {}", dto.email());

        return generateTokens(
                user.getEmail(),
                user.getFullName(),
                user.getRole()
        );
    }

    public AuthResponseDTO refreshToken(RefreshTokenRequestDTO dto) {

        log.debug("Solicitud de refresh token");

        RefreshToken refreshToken = refreshTokenService
                .verifyRefreshToken(dto.refreshToken());

        User user = userRepository.findByEmailAndActiveTrue(
                        refreshToken.getUserEmail())
                .orElseThrow(() -> new AuthException(
                        "Usuario no encontrado o inactivo"
                ));

        String newAccessToken = jwtUtil.generateAccessToken(
                user.getEmail(),
                user.getRole()
        );

        log.info("Access token renovado para: {}", user.getEmail());

        return new AuthResponseDTO(
                newAccessToken,
                dto.refreshToken(),
                "Bearer",
                jwtUtil.getAccessExpirationMs(),
                user.getEmail(),
                user.getFullName(),
                user.getRole()
        );
    }

    @Transactional
    public void logout(String refreshToken) {

        log.debug("Cerrando sesion");

        refreshTokenService.revokeRefreshToken(refreshToken);

        log.info("Sesion cerrada exitosamente");
    }

    private AuthResponseDTO generateTokens(
            String email,
            String fullName,
            String role) {

        String accessToken = jwtUtil.generateAccessToken(email, role);

        RefreshToken refreshToken = refreshTokenService
                .createRefreshToken(email);

        return AuthResponseDTO.of(
                accessToken,
                refreshToken.getToken(),
                jwtUtil.getAccessExpirationMs(),
                email,
                fullName,
                role
        );
    }

    public List<UserResponseDTO> getAllUsers() {
        return userRepository.findByActiveTrue()
                .stream()
                .map(u -> UserResponseDTO.builder()
                        .id(u.getId())
                        .fullName(u.getFullName())
                        .email(u.getEmail())
                        .city(u.getCity())
                        .country(u.getCountry())
                        .role(u.getRole())
                        .active(u.getActive())
                        .createdAt(u.getCreatedAt())
                        .build())
                .toList();
    }
}
