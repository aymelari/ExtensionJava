package org.example.myextension.Security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String username;

        // Check if the Authorization header is present and starts with "Bearer "
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        // Extract the JWT token and username
        jwt = authHeader.replace("Bearer ", "");
        username = jwtService.extractUsername(jwt);

        // Log the JWT token and extracted username
        System.out.println("JWT: " + jwt);
        System.out.println("Username extracted from JWT: " + username);

        // Validate the token and set the authentication in the SecurityContext
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

            // Log the user details and roles
            System.out.println("User Details: " + userDetails);
            System.out.println("User Roles: " + userDetails.getAuthorities());

            // Validate the token and check if it is valid for the user
            if (jwtService.isTokenValid(jwt, userDetails)) {
                System.out.println("Token is valid");

                // Create an authentication token
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );

                // Set additional details (e.g., IP address, session ID)
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // Set the authentication in the SecurityContext
                SecurityContextHolder.getContext().setAuthentication(authToken);

                // Log the authenticated user
                System.out.println("Authenticated User: " + SecurityContextHolder.getContext().getAuthentication());
            } else {
                System.out.println("Token is invalid");
            }
        }

        // Continue the filter chain
        filterChain.doFilter(request, response);
        System.out.println("DO FILTER");
    }
}