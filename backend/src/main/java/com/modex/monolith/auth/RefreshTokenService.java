package com.modex.monolith.auth;

import com.modex.monolith.security.JwtUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Slf4j
@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtUtil jwtUtil;

    @Transactional
    public RefreshToken createRefreshToken(String userEmail) {

        log.debug("Creando refresh token para: {}", userEmail);

        // Solo una sesion activa a la vez por usuario
        refreshTokenRepository.deleteByUserEmail(userEmail);

        Instant expiresAt = Instant.now()
                .plusMillis(jwtUtil.getRefreshExpirationMs());

        RefreshToken refreshToken = RefreshToken.builder()
                .token(jwtUtil.generateRefreshToken())
                .userEmail(userEmail)
                .expiresAt(expiresAt)
                .revoked(false)
                .build();

        RefreshToken saved = refreshTokenRepository.save(refreshToken);

        log.info("Refresh token creado para: {}", userEmail);

        return saved;
    }

    public RefreshToken verifyRefreshToken(String token) {

        log.debug("Verificando refresh token");

        RefreshToken refreshToken = refreshTokenRepository
                .findByToken(token)
                .orElseThrow(() -> {
                    log.warn("Refresh token no encontrado en MongoDB");
                    return new AuthException("Refresh token invalido o no existe");
                });

        if (refreshToken.getRevoked()) {
            log.warn("Intento de uso de refresh token revocado");
            refreshTokenRepository.delete(refreshToken);
            throw new AuthException("Refresh token fue revocado");
        }

        if (Instant.now().isAfter(refreshToken.getExpiresAt())) {
            log.warn("Refresh token expirado para: {}",
                    refreshToken.getUserEmail());
            refreshTokenRepository.delete(refreshToken);
            throw new AuthException("Refresh token expirado");
        }

        log.debug("Refresh token valido para: {}",
                refreshToken.getUserEmail());

        return refreshToken;
    }

    @Transactional
    public void revokeRefreshToken(String token) {

        log.debug("Revocando refresh token");

        refreshTokenRepository.findByToken(token)
                .ifPresent(refreshToken -> {
                    refreshToken.setRevoked(true);
                    refreshTokenRepository.save(refreshToken);
                    log.info("Refresh token revocado para: {}",
                            refreshToken.getUserEmail());
                });
    }

    @Transactional
    public void revokeAllUserTokens(String userEmail) {

        log.debug("Revocando todos los tokens de: {}", userEmail);

        refreshTokenRepository.deleteByUserEmail(userEmail);

        log.info("Todos los tokens revocados para: {}", userEmail);
    }
}
