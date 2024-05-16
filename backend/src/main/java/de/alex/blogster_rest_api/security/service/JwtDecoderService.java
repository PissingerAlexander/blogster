package de.alex.blogster_rest_api.security.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import de.alex.blogster_rest_api.security.config.JwtConfiguration;
import org.springframework.stereotype.Component;

@Component
public class JwtDecoderService {
    private final JwtConfiguration jwtConfiguration;

    public JwtDecoderService(JwtConfiguration jwtConfiguration) {
        this.jwtConfiguration = jwtConfiguration;
    }

    public DecodedJWT decode(String token) {
        return JWT.require(Algorithm.HMAC512(jwtConfiguration.getSecret()))
                .build()
                .verify(token);
    }
}
