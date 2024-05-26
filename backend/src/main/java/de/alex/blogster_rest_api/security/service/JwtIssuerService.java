package de.alex.blogster_rest_api.security.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import de.alex.blogster_rest_api.security.config.JwtConfiguration;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Component
public class JwtIssuerService {
    private final JwtConfiguration jwtConfiguration;

    public JwtIssuerService(JwtConfiguration jwtConfiguration) {
        this.jwtConfiguration = jwtConfiguration;
    }

    public String issue(long id, String username, List<String> authorities) {
        return JWT.create()
                .withSubject(String.valueOf(id))
                .withExpiresAt(Instant.now().plus(Duration.of(12, ChronoUnit.HOURS)))
                .withClaim("username", username)
                .withClaim("authorities", authorities)
                .sign(Algorithm.HMAC512(jwtConfiguration.getSecret()));
    }
}
