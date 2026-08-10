package com.tss.loanEmiSchedular.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Triggered whenever an unauthenticated request hits a protected endpoint
 * (i.e. no valid JWT was supplied at all). Returns 401. This is distinct
 * from AccessDeniedException (403), which fires when the JWT IS valid but
 * the user's role isn't allowed on that endpoint.
 */
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request,
                          HttpServletResponse response,
                          AuthenticationException authException) throws IOException, ServletException {

        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, authException.getMessage());
    }
}
