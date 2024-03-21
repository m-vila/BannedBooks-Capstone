package org.martavila.bannedbooks.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;

public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {

        // Retrieve the role of the authenticated user
        String role = authentication.getAuthorities().stream().findFirst().get().getAuthority();
        // Define the target URL based on the user's role
        String targetUrl;


        if ("ROLE_USER".equals(role)) {
            // If the user is a regular user, redirect to the user dashboard
            targetUrl = "/user-dashboard";
        } else if ("ROLE_ADMIN".equals(role)) {
            // If the user is an admin, redirect to the admin dashboard
            targetUrl = "/admin-dashboard";
        } else {
            // Default redirect to the user dashboard
            targetUrl = "/user-dashboard";
        }

        // Redirect the user to the appropriate dashboard based on their role
        response.sendRedirect(targetUrl);
    }
}
