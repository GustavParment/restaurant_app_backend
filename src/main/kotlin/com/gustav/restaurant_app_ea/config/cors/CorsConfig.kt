package com.gustav.restaurant_app_ea.config.cors

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.UrlBasedCorsConfigurationSource

@Configuration
class CorsConfig {
    @Bean
        fun corsConfigurationSource(): UrlBasedCorsConfigurationSource {
            val corsConfig = CorsConfiguration()
            corsConfig.allowedOrigins = listOf("http://localhost:3000")
            corsConfig.allowedMethods = listOf("GET", "POST", "PUT", "DELETE", "OPTIONS")
            corsConfig.allowedHeaders = listOf("Authorization", "Content-Type")
            corsConfig.allowCredentials = true
            corsConfig.maxAge = 3600L

            val source = UrlBasedCorsConfigurationSource()
            source.registerCorsConfiguration("/**", corsConfig)
            return source
        }
    }


