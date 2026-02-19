package com.harshi_solution.user;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.harshi_solution.user.authenticationEntryPoint.CustomAccessDeniedHandler;
import com.harshi_solution.user.authenticationEntryPoint.CustomAuthenticationEntryPoint;
import com.harshi_solution.user.authenticationProvider.JWTAuthenticationProvider;
import com.harshi_solution.user.filter.JWTAuthenticationFilter;
import com.harshi_solution.user.filter.JWTRefreshFilter;
import com.harshi_solution.user.filter.JWTValidationFilter;
import com.harshi_solution.user.util.JWTUtil;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private JWTUtil jwtUtil;
    private UserDetailsService userDetailsService;
    private CustomAuthenticationEntryPoint customAuthenticationEntryPoint;
    private CustomAccessDeniedHandler customAccessDeniedHandler;

    public SecurityConfig(JWTUtil jwtUtil, UserDetailsService userDetailsService,
            CustomAuthenticationEntryPoint customAuthenticationEntryPoint,
            CustomAccessDeniedHandler customAccessDeniedHandler) {
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
        this.customAuthenticationEntryPoint = customAuthenticationEntryPoint;
        this.customAccessDeniedHandler = customAccessDeniedHandler;
    }

    @Bean
    public JWTAuthenticationProvider jwtAuthenticationProvider() {
        return new JWTAuthenticationProvider(jwtUtil, userDetailsService);
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(
            PasswordEncoder passwordEncoder) {

        DaoAuthenticationProvider provider = new DaoAuthenticationProvider(userDetailsService);

        provider.setPasswordEncoder(passwordEncoder);
        return provider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, AuthenticationManager authenticationManager,
            JWTUtil jwtUtil) throws Exception {

        // Authentication filter responsible for login
        JWTAuthenticationFilter jwtAuthFilter = new JWTAuthenticationFilter(authenticationManager, jwtUtil);

        // Validation filter for checking JWT in every request
        JWTValidationFilter jwtValidationFilter = new JWTValidationFilter(authenticationManager);

        // refresh filter for checking JWT in every request
        JWTRefreshFilter jwtRefreshFilter = new JWTRefreshFilter(jwtUtil, authenticationManager);

        http.authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/v1/user/login", "/api/v1/user/change-password", "/api/v1/user/log-out")
                .permitAll()
                .requestMatchers("/api/v1/admin/**").hasRole("ADMIN")
                .anyRequest().authenticated())
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .csrf(csrf -> csrf.disable())
                .exceptionHandling(ex -> ex
                        .authenticationEntryPoint(customAuthenticationEntryPoint))
                .exceptionHandling(ex -> ex
                        .authenticationEntryPoint(customAuthenticationEntryPoint)
                        .accessDeniedHandler(customAccessDeniedHandler))
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class) // generate token filter
                .addFilterAfter(jwtValidationFilter, JWTAuthenticationFilter.class) // validate token filter
                .addFilterAfter(jwtRefreshFilter, JWTValidationFilter.class); // refresh token filter
        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            DaoAuthenticationProvider daoAuthenticationProvider,
            JWTAuthenticationProvider jwtAuthenticationProvider) {

        return new ProviderManager(
                Arrays.asList(
                        daoAuthenticationProvider,
                        jwtAuthenticationProvider));
    }

}
