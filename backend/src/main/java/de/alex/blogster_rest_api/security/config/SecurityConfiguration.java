package de.alex.blogster_rest_api.security.config;

import de.alex.blogster_rest_api.security.encoder.PwdEncoder;
import de.alex.blogster_rest_api.security.filter.JwtAuthenticationFilter;
import de.alex.blogster_rest_api.security.service.CustomUserDetailService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final CustomUserDetailService customUserDetailService;

    public SecurityConfiguration(JwtAuthenticationFilter jwtAuthenticationFilter, CustomUserDetailService customUserDetailService) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        this.customUserDetailService = customUserDetailService;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        httpSecurity
                .cors(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .formLogin(AbstractHttpConfigurer::disable)
                .securityMatcher("/**")
                .authorizeHttpRequests(registry -> registry
                        .requestMatchers("/user").permitAll()
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
