package de.alex.blogster_rest_api.authentication.service;

import de.alex.blogster_rest_api.authentication.model.LoginResponse;
import de.alex.blogster_rest_api.role.model.Role;
import de.alex.blogster_rest_api.security.authentication.UserPrincipal;
import de.alex.blogster_rest_api.security.service.JwtIssuerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {
    private final JwtIssuerService jwtIssuerService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationService(JwtIssuerService jwtIssuerService, AuthenticationManager authenticationManager) {
        this.jwtIssuerService = jwtIssuerService;
        this.authenticationManager = authenticationManager;
    }


    public ResponseEntity<LoginResponse> attemptLogin(String username, String password) {
        System.out.println("attempt login");
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password)
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        Role role = Role.valueOf(
                userPrincipal.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority)
                        .toList().get(0)
        );
        String token = jwtIssuerService.issue(userPrincipal.getUuid(), userPrincipal.getUsername(), userPrincipal.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList());
        return new ResponseEntity<>(new LoginResponse(token), HttpStatus.OK);
    }
}
