package com.harshi_solution.transport.authenticationprovider;


import java.util.List;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.harshi_solution.transport.token.JWTAuthenticationToken;
import com.harshi_solution.transport.util.JWTUtil;

public class JWTAuthenticationProvider implements AuthenticationProvider {

    private JWTUtil jwtUtil;

    public JWTAuthenticationProvider(JWTUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    public Authentication authenticate(Authentication authentication)
            throws AuthenticationException {

        String token = ((JWTAuthenticationToken) authentication).getToken();

        String username = jwtUtil.validateAndExtractUsername(token);
        if (username == null) {
            throw new BadCredentialsException("Invalid JWT Token");
        }

        // 🔥 NEW: Validate token type
        String type = jwtUtil.extractTokenType(token);
        if (!"access".equals(type)) {
            throw new BadCredentialsException("Invalid token type");
        }

        // 🔥 NEW: Extract role from token (NO DB CALL)
        String role = jwtUtil.extractRole(token);
        if (role == null) {
            throw new BadCredentialsException("Role not found in token");
        }

        List<GrantedAuthority> authorities =
                List.of(new SimpleGrantedAuthority(role));

        return new UsernamePasswordAuthenticationToken(
                username,
                null,
                authorities
        );
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return JWTAuthenticationToken.class.isAssignableFrom(authentication);
    }
}


