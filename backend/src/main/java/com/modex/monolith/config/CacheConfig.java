// ============================================================
// CacheConfig.java - Cache en memoria (reemplaza Redis)
// En los microservicios originales Product Service usaba
// Redis para cachear productos. Para que el despliegue sea
// 100% gratis usamos el cache en memoria de Spring
// (ConcurrentMapCacheManager) en vez de un servicio externo.
//
// Las anotaciones @Cacheable y @CacheEvict del ProductService
// funcionan exactamente igual — solo cambia donde se guarda
// el cache (RAM de la app en vez de un servidor Redis aparte).
//
// Diferencia importante: este cache es por instancia de la
// app y se pierde si la app se reinicia (en Render free el
// servicio se duerme tras 15 min de inactividad). Para un
// proyecto de portafolio esto es aceptable.
// ============================================================

package com.modex.monolith.config;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@EnableCaching
@Configuration
public class CacheConfig {

    @Bean
    public ConcurrentMapCacheManager cacheManager() {
        // "products" es el unico cache que usamos, igual que
        // el value = "products" en las anotaciones @Cacheable
        // del ProductService original.
        return new ConcurrentMapCacheManager("products");
    }
}
