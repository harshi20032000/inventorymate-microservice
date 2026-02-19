package com.harshi_solution.user.authenticationProvider;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.harshi_solution.user.token.JWTAuthenticationToken;
import com.harshi_solution.user.util.JWTUtil;

public class JWTAuthenticationProvider implements AuthenticationProvider {

    private JWTUtil jwtUtil;
    private UserDetailsService userDetailsService;

    public JWTAuthenticationProvider(JWTUtil jwtUtil, UserDetailsService userDetailsService) {
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String token = ((JWTAuthenticationToken) authentication).getToken();

        String username = jwtUtil.validateAndExtractUsername(token);
        if (username == null) {
            throw new BadCredentialsException("Invalid JWT Token");
        }

        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return JWTAuthenticationToken.class.isAssignableFrom(authentication);
    }
}

