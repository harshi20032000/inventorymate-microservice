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
        public SecurityFilterChain securityFilterChain(
                        HttpSecurity http,
                        AuthenticationManager authenticationManager) throws Exception {

                JWTValidationFilter jwtValidationFilter = new JWTValidationFilter(authenticationManager);

                http.authorizeHttpRequests(auth -> auth
                                .requestMatchers("/api/v1/user/login").permitAll()
                                .requestMatchers("/api/v1/user/refresh-token").permitAll()
                                .requestMatchers("/api/v1/user/change-password").authenticated()
                                .requestMatchers("/api/v1/user/log-out").authenticated()
                                .requestMatchers("/api/v1/admin/**").hasRole("ADMIN")
                                .anyRequest().authenticated())
                                .sessionManagement(session -> session
                                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                                .csrf(csrf -> csrf.disable())
                                .exceptionHandling(ex -> ex
                                                .authenticationEntryPoint(customAuthenticationEntryPoint)
                                                .accessDeniedHandler(customAccessDeniedHandler))
                                // 👇 Only one filter now
                                .addFilterBefore(jwtValidationFilter,
                                                UsernamePasswordAuthenticationFilter.class);

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
