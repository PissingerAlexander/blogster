package de.alex.blogster_rest_api.authentication.controller;

import de.alex.blogster_rest_api.authentication.model.LoginRequest;
import de.alex.blogster_rest_api.authentication.model.LoginResponse;
import de.alex.blogster_rest_api.authentication.model.RegisterRequest;
import de.alex.blogster_rest_api.authentication.service.AuthenticationService;
import de.alex.blogster_rest_api.user.model.User;
import de.alex.blogster_rest_api.user.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {
    private final UserService userService;
    private final AuthenticationService authenticationService;

    public AuthenticationController(UserService userService, AuthenticationService authenticationService) {
        this.userService = userService;
        this.authenticationService = authenticationService;
    }

    @PostMapping(path = "/login", consumes = "application/json", produces = "application/json")
    public ResponseEntity<LoginResponse> login(@RequestBody @Validated LoginRequest loginRequest) {
        return new ResponseEntity<>(authenticationService.attemptLogin(loginRequest.getUsername(), loginRequest.getPassword()), HttpStatus.OK);
    }

    @PostMapping(path = "/register", consumes = "application/json", produces = "text/plain")
    public ResponseEntity<String> register(@RequestBody @Validated RegisterRequest registerRequest) {
        if (userService.findUserByUsername(registerRequest.getUsername()) != null)
            return new ResponseEntity<>("Username already exists", HttpStatus.CONFLICT);
        if (userService.findUserByMailAddress(registerRequest.getMailAddress()) != null)
            return new ResponseEntity<>("E-Mail address already used", HttpStatus.CONFLICT);

        User newUser = new User(
                registerRequest.getUsername(),
                registerRequest.getPassword(),
                registerRequest.getFullName() == null ? "" : registerRequest.getFullName(),
                registerRequest.getMailAddress()
        );
        userService.createUser(newUser);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }
}
