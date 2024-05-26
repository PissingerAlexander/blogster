package de.alex.blogster_rest_api.security.config;

import de.alex.blogster_rest_api.security.encoder.PwdEncoder;
import de.alex.blogster_rest_api.security.filter.CorsFilter;
import de.alex.blogster_rest_api.security.filter.JwtAuthenticationFilter;
import de.alex.blogster_rest_api.security.service.CustomUserDetailService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.session.SessionManagementFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
    private final CorsFilter corsFilter;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final CustomUserDetailService customUserDetailService;

    public SecurityConfiguration(CorsFilter corsFilter, JwtAuthenticationFilter jwtAuthenticationFilter, CustomUserDetailService customUserDetailService) {
        this.corsFilter = corsFilter;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        this.customUserDetailService = customUserDetailService;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.addFilterBefore(corsFilter, SessionManagementFilter.class);
        httpSecurity.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        httpSecurity
                .cors(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .sessionManagement(sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .securityMatcher("/**")
                .authorizeHttpRequests(registry -> registry
                        .requestMatchers(HttpMethod.OPTIONS).permitAll()
                        .requestMatchers("/user/**").authenticated()        // /blog neuen blog, /comment, /posts auf bestimmten blog
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .requestMatchers("/auth/*").permitAll()
                        .anyRequest().authenticated()
                );

        return httpSecurity.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PwdEncoder.passwordEncoder;
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity httpSecurity) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder = httpSecurity.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder
                .userDetailsService(customUserDetailService)
                .passwordEncoder(passwordEncoder());
        return authenticationManagerBuilder.build();
    }
}
