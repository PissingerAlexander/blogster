package de.alex.blogster_rest_api.authentication.controller;

import de.alex.blogster_rest_api.authentication.model.LoginRequest;
import de.alex.blogster_rest_api.authentication.model.LoginResponse;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @PostMapping(path = "/login", consumes = "application/json", produces = "application/json")
    public LoginResponse login(@RequestBody @Validated LoginRequest loginRequest) {
        return new LoginResponse("bla bla bla");
    }
}
