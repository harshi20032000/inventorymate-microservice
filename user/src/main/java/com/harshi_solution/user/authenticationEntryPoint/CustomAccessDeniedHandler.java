package com.harshi_solution.user.authenticationEntryPoint;

import java.io.IOException;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.harshi_solution.user.dto.BaseUIResponse;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class CustomAccessDeniedHandler  implements AccessDeniedHandler {
@Override
    public void handle(HttpServletRequest request,
                       HttpServletResponse response,
                       AccessDeniedException accessDeniedException)
            throws IOException {

        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);

        BaseUIResponse<String> errorResponse = new BaseUIResponse<>();
        errorResponse.setHasError(true);
        errorResponse.setCode("403");
        errorResponse.setMessage("Access Denied");
        errorResponse.setExtendedMessage(accessDeniedException.getMessage());

        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(response.getOutputStream(), errorResponse);
    }

}
