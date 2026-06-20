package com.modex.monolith.auth;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.index.Indexed;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.time.LocalDateTime;

@Document(collection = "refresh_tokens")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RefreshToken {

    @Id
    private String id;

    @Indexed(unique = true)
    @Field("token")
    private String token;

    @Field("user_email")
    private String userEmail;

    @Field("expires_at")
    private Instant expiresAt;

    @Builder.Default
    @Field("revoked")
    private Boolean revoked = false;

    @CreatedDate
    @Field("created_at")
    private LocalDateTime createdAt;
}
