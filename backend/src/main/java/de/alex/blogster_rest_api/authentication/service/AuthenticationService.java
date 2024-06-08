package de.alex.blogster_rest_api.authentication.service;

import de.alex.blogster_rest_api.authentication.model.login.LoginResponse;
import de.alex.blogster_rest_api.security.authentication.UserPrincipal;
import de.alex.blogster_rest_api.security.service.JwtIssuerService;
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


    public LoginResponse attemptLogin(String username, String password) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password)
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        String token = jwtIssuerService.issue(userPrincipal.getId(), userPrincipal.getUsername(), userPrincipal.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList());
        return new LoginResponse(token);
    }
}
