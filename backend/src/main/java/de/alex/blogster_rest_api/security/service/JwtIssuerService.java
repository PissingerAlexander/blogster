package de.alex.blogster_rest_api.security.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import de.alex.blogster_rest_api.role.model.Role;
import de.alex.blogster_rest_api.security.config.JwtConfiguration;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;

@Component
public class JwtIssuerService {
    private final JwtConfiguration jwtConfiguration;

    public JwtIssuerService(JwtConfiguration jwtConfiguration) {
        this.jwtConfiguration = jwtConfiguration;
    }

    public String issue(UUID uuid, String username, List<String> authorities) {
        return JWT.create()
                .withSubject(uuid.toString())
                .withExpiresAt(Instant.now().plus(Duration.of(12, ChronoUnit.HOURS)))
                .withClaim("username", username)
                .withClaim("authorities", authorities)
                .sign(Algorithm.HMAC512(jwtConfiguration.getSecret()));
    }
}
