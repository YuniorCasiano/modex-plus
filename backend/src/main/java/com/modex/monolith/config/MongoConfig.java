// ============================================================
// MongoConfig.java - Configuracion de MongoDB
// Activa las fechas automaticas @CreatedDate y
// @LastModifiedDate en los documentos de MongoDB.
// Unica para todo el monolito (antes estaba duplicada
// en auth-service, user-service y product-service).
// ============================================================

package com.modex.monolith.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

@Configuration
@EnableMongoAuditing
@EnableJpaAuditing
public class MongoConfig {
}
