package com.harshi_solution.transport;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.harshi_solution.transport.authenticationprovider.JWTAuthenticationProvider;
import com.harshi_solution.transport.filter.JWTValidationFilter;
import com.harshi_solution.transport.util.JWTUtil;
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JWTUtil jwtUtil;

    public SecurityConfig(JWTUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Bean
    public JWTAuthenticationProvider jwtAuthenticationProvider() {
        return new JWTAuthenticationProvider(jwtUtil);
    }

    @Bean
    public AuthenticationManager authenticationManager(
            JWTAuthenticationProvider jwtAuthenticationProvider) {

        return new ProviderManager(List.of(jwtAuthenticationProvider));
    }

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http,
            AuthenticationManager authenticationManager) throws Exception {

        JWTValidationFilter jwtValidationFilter =
                new JWTValidationFilter(authenticationManager);

        http
            .csrf(csrf -> csrf.disable())
            .sessionManagement(session ->
                    session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth -> auth
                    .requestMatchers("/api/v1/party/public/**").permitAll()
                    .requestMatchers("/api/v1/party/admin/**").hasRole("ADMIN")
                    .anyRequest().authenticated()
            )
            .addFilterBefore(jwtValidationFilter,
                    UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}