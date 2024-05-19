package de.alex.blogster_rest_api.authentication.controller;

import de.alex.blogster_rest_api.authentication.model.LoginRequest;
import de.alex.blogster_rest_api.authentication.model.LoginResponse;
import de.alex.blogster_rest_api.authentication.model.RegisterRequest;
import de.alex.blogster_rest_api.role.model.Role;
import de.alex.blogster_rest_api.security.authentication.UserPrincipal;
import de.alex.blogster_rest_api.security.encoder.PwdEncoder;
import de.alex.blogster_rest_api.security.service.JwtIssuerService;
import de.alex.blogster_rest_api.user.model.User;
import de.alex.blogster_rest_api.user.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {
    private final UserService userService;
    private final JwtIssuerService jwtIssuer;
    private final AuthenticationManager authenticationManager;

    public AuthenticationController(UserService userService, JwtIssuerService jwtIssuer, AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.jwtIssuer = jwtIssuer;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping(path = "/login", consumes = "application/json", produces = "application/json")
    public ResponseEntity<LoginResponse> login(@RequestBody @Validated LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        Role role = Role.valueOf(
                userPrincipal.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority)
                        .toList().get(0)
        );
        String token = jwtIssuer.issue(userPrincipal.getUuid(), userPrincipal.getUsername(), role);
        return new ResponseEntity<>(new LoginResponse(token), HttpStatus.OK);
    }

    @PostMapping(path = "/register", consumes = "application/json", produces = "text/plain")
    public ResponseEntity<String> register(@RequestBody @Validated RegisterRequest registerRequest) {
        if (userService.findUserByUsername(registerRequest.getUsername()) != null)
            return new ResponseEntity<>("Username already exists", HttpStatus.CONFLICT);
        if (userService.findUserByMailAddress(registerRequest.getMailAddress()) != null)
            return new ResponseEntity<>("E-Mail address already used", HttpStatus.CONFLICT);

        User newUser = new User(
                registerRequest.getUsername(),
                PwdEncoder.passwordEncoder.encode(registerRequest.getPassword()),
                registerRequest.getFullName() == null ? "" : registerRequest.getFullName(),
                registerRequest.getMailAddress()
        );
        userService.createUser(newUser);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }
}
