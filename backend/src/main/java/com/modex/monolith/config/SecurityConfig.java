// ============================================================
// SecurityConfig.java - Seguridad unica del monolito
// Combina las reglas de autorizacion que antes estaban
// repartidas en 5 SecurityConfig distintos (uno por
// microservicio). El orden de las reglas importa: la primera
// que haga match con la peticion gana.
//
// Reglas combinadas de:
// - auth-service:    /api/auth/register, /api/auth/login publicos;
//                     GET /api/auth/users solo ADMIN
// - product-service:  GET /api/products/** publico (catalogo)
// - order-service:    GET /api/orders y PUT estado solo ADMIN
// - inventory-service: todo requiere autenticacion
// - user-service:     antes sin seguridad real (modo permisivo)
// ============================================================

package com.modex.monolith.config;

import com.modex.monolith.security.JwtAuthenticationFilter;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthFilter;

    // Lee app.cors.allowed-origins desde application.properties.
    // En local es "*" (todo permitido). En produccion (Render)
    // es la URL exacta del frontend en Vercel, por seguridad.
    @Value("${app.cors.allowed-origins:*}")
    private String allowedOrigins;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)
            throws Exception {

        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authorizeHttpRequests(auth -> auth
                        // ── Publico: salud del servicio (Render lo usa) ──
                        .requestMatchers("/actuator/health").permitAll()

                        // ── Auth: registro y login son publicos ──
                        .requestMatchers("/api/auth/register").permitAll()
                        .requestMatchers("/api/auth/login").permitAll()
                        .requestMatchers("/api/auth/refresh").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/auth/users").hasAuthority("ADMIN")

                        // ── Products: el catalogo (GET) es publico ──
                        .requestMatchers(HttpMethod.GET, "/api/products/**").permitAll()

                        // ── Orders: listar todos y cambiar estado, solo ADMIN ──
                        .requestMatchers(HttpMethod.GET, "/api/orders").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/orders/*/status").hasAuthority("ADMIN")

                        // ── Todo lo demas requiere estar autenticado ──
                        .anyRequest().authenticated()
                )
                .addFilterBefore(
                        jwtAuthFilter,
                        UsernamePasswordAuthenticationFilter.class
                );

        return http.build();
    }

    // CORS para que el frontend en Vercel pueda llamar a este
    // backend desde el navegador. allowedOriginPatterns se
    // configura via application.properties (app.cors.allowed-origins).
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        // Soporta una o varias URLs separadas por coma en
        // app.cors.allowed-origins, ej:
        // https://modex-plus.vercel.app,https://www.midominio.com
        configuration.setAllowedOriginPatterns(
                Arrays.asList(allowedOrigins.split(",")));
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("*"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
