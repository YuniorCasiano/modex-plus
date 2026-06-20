// ============================================================
// JwtAuthenticationFilter.java - Filtro de autenticacion JWT
// Es un filtro que Spring ejecuta en CADA peticion HTTP
// antes de que llegue al Controller.
//
// Antes estaba duplicado en cada microservicio. En el
// monolito es uno solo, registrado una vez en SecurityConfig
// y aplicado a todos los endpoints.
//
// Lo que hace en cada peticion:
// 1. Lee el header Authorization
// 2. Extrae el token JWT
// 3. Verifica que el token sea valido y que el usuario exista
// 4. Si es valido registra al usuario como autenticado
//    en el contexto de Spring Security (con su rol)
// 5. La peticion continua al Controller
// ============================================================

package com.modex.monolith.security;

import com.modex.monolith.users.UserRepository;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)
            throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        final String token = authHeader.substring(7);

        try {
            final String email = jwtUtil.extractEmail(token);

            if (email != null &&
                    SecurityContextHolder.getContext()
                            .getAuthentication() == null) {

                // Verificamos que el usuario existe en MongoDB
                // y que el token es valido para ese usuario
                boolean userExists = userRepository.existsByEmail(email);

                if (userExists && jwtUtil.isTokenValid(token, email)) {

                    String role = jwtUtil.extractRole(token);
                    String authority = (role != null) ? role : "CLIENTE";

                    UserDetails userDetails = User.builder()
                            .username(email)
                            .password("")
                            .authorities(authority)
                            .build();

                    UsernamePasswordAuthenticationToken authToken =
                            new UsernamePasswordAuthenticationToken(
                                    userDetails,
                                    null,
                                    userDetails.getAuthorities()
                            );

                    authToken.setDetails(
                            new WebAuthenticationDetailsSource()
                                    .buildDetails(request)
                    );

                    SecurityContextHolder.getContext()
                            .setAuthentication(authToken);

                    log.debug("Usuario autenticado via JWT: {} con rol: {}", email, authority);
                }
            }
        } catch (Exception e) {
            log.debug("Error procesando JWT: {}", e.getMessage());
        }

        filterChain.doFilter(request, response);
    }
}
