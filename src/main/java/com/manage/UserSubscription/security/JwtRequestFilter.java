package com.manage.UserSubscription.security;

import com.manage.UserSubscription.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;  // Add this annotation
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component  // Ensure JwtRequestFilter is a Spring-managed bean
public class JwtRequestFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // Extract the JWT token from the Authorization header
        String token = extractTokenFromHeader(request);

        if (token != null && jwtUtil.validateToken(token, jwtUtil.extractUsername(token))) {
            // Extract userId from the token
            String userId = jwtUtil.extractUserId(token);

            // Add userId to the request attributes for use in the controller
            request.setAttribute("userId", userId);
        }

        // Continue with the filter chain
        filterChain.doFilter(request, response);
    }

    // Helper method to extract the token from the Authorization header
    private String extractTokenFromHeader(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");

        // Check if the Authorization header is present and starts with "Bearer "
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7);  // Extract token after "Bearer "
        }
        return null;
    }
}
