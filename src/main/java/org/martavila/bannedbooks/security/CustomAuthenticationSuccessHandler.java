package org.martavila.bannedbooks.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;

public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        String role = authentication.getAuthorities().stream().findFirst().get().getAuthority();
        String targetUrl;
        if ("ROLE_USER".equals(role)) {
            targetUrl = "/books";
        } else if ("ROLE_ADMIN".equals(role)) {
            targetUrl = "/admin-dashboard";
        } else {
            targetUrl = "/books";
        }
        response.sendRedirect(targetUrl);
    }
}
