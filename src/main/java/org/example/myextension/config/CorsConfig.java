package org.example.myextension.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;

@Configuration
public class CorsConfig {







    @Bean
    public FilterRegistrationBean<CorsFilter> corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(Arrays.asList(
                "http://localhost:8081",
                "chrome-extension://oilfjkdlapocgipffjnkpkpmloefbpkf",
                "https://www.zara.com",
                "https://www.bershka.com/",
                "https://www.pullandbear.com/",
                "https://shop.mango.com/",
                "https://www.trendyol.com/",
                "https://www.temu.com/",
                "https://www.stradivarius.com/"
        ));
        config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(Arrays.asList("Content-Type", "Authorization"));
        config.setAllowCredentials(true);  // Allow cookies if needed
        source.registerCorsConfiguration("/**", config);

        FilterRegistrationBean<CorsFilter> bean = new FilterRegistrationBean<>(new CorsFilter(source));
        bean.setOrder(0); // Set to the highest priority
        return bean;
    }
}
