package org.example.myextension.Security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http

                .cors(cors -> cors.configurationSource(corsConfigurationSource()))

                .csrf(csrf -> csrf.disable()) // Disable CSRF
                .headers(headers -> headers.frameOptions(frame -> frame.sameOrigin())) // Allow H2 console
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/v1/auth/**", "/h2-console/**","/api/wishList/add").permitAll()

                        .anyRequest().authenticated()// All other requests require authentication
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Stateless sessions
                .authenticationProvider(authenticationProvider) // Custom auth provider
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
                // Add JWT filter.add()
        return http.build();
    }


    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();

        // Specify exactly which origins are allowed
        // (use config.setAllowedOriginPatterns if you need wildcards)
        config.setAllowedOrigins(Arrays.asList(
                "http://localhost:8081",
                "chrome-extension://oilfjkdlapocgipffjnkpkpmloefbpkf",
                "https://www.zara.com",
                "https://www.bershka.com",
                "https://www.pullandbear.com",
                "https://shop.mango.com",
                "https://www.trendyol.com/",
                "https://www.temu.com/",
                "https://www.stradivarius.com/"
        ));

        // Allowed HTTP methods
        config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));

        // Allowed headers
        config.setAllowedHeaders(Arrays.asList("Content-Type", "Authorization"));

        // If you need credentials (cookies / auth headers in cross-site)
        config.setAllowCredentials(true);

        // Apply this config to all endpoints
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);

        return source;
    }


}
