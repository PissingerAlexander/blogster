package de.alex.blogster_rest_api.security.filter;

import de.alex.blogster_rest_api.security.authentication.UserPrincipalAuthenticationToken;
import de.alex.blogster_rest_api.security.service.JwtDecoderService;
import de.alex.blogster_rest_api.security.service.JwtToPrincipalConverterService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtDecoderService jwtDecoderService;
    private final JwtToPrincipalConverterService jwtToPrincipalConverterService;

    public JwtAuthenticationFilter(JwtDecoderService jwtDecoderService, JwtToPrincipalConverterService jwtToPrincipalConverterService) {
        this.jwtDecoderService = jwtDecoderService;
        this.jwtToPrincipalConverterService = jwtToPrincipalConverterService;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        extractTokenFromRequest(request)
                .map(jwtDecoderService::decode)
                .map(jwtToPrincipalConverterService::convert)
                .map(UserPrincipalAuthenticationToken::new)
                .ifPresent(authentication -> SecurityContextHolder.getContext().setAuthentication(authentication));

        filterChain.doFilter(request, response);
    }

    private Optional<String> extractTokenFromRequest(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if (StringUtils.hasText(token) && token.startsWith("Bearer ")) {
            return Optional.of(token.substring(7));
        }
        return Optional.empty();
    }
}
