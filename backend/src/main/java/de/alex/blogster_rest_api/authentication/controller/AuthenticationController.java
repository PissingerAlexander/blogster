package de.alex.blogster_rest_api.authentication.controller;

import de.alex.blogster_rest_api.authentication.model.LoginRequest;
import de.alex.blogster_rest_api.authentication.model.LoginResponse;
import de.alex.blogster_rest_api.role.model.Role;
import de.alex.blogster_rest_api.security.service.JwtIssuer;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {
    private final JwtIssuer jwtIssuer;

    public AuthenticationController(JwtIssuer jwtIssuer) {
        this.jwtIssuer = jwtIssuer;
    }

    @PostMapping(path = "/login", consumes = "application/json", produces = "application/json")
    public LoginResponse login(@RequestBody @Validated LoginRequest loginRequest) {
        String token = jwtIssuer.issue(UUID.randomUUID(), loginRequest.getUsername(), Role.ADMIN);
        return new LoginResponse(token);
    }
}
