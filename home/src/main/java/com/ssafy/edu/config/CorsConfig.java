package com.ssafy.edu.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig {

    @Bean
    WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**") // 모든 엔드포인트에 대해
                		.allowedOrigins("http://localhost:5173", "http://localhost:5174", "http://localhost:5000") // Vue 개발 서버 URL
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS", HttpMethod.GET.name(), HttpMethod.POST.name(), HttpMethod.PUT.name(),
        						HttpMethod.DELETE.name(), HttpMethod.HEAD.name(), HttpMethod.OPTIONS.name(),
        						HttpMethod.PATCH.name())
                        .allowCredentials(true);
            }
        };
    }
}
