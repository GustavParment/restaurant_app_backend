package com.gustav.restaurant_app_ea.security

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Role
import org.springframework.security.config.Customizer
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer
import org.springframework.security.web.SecurityFilterChain


@Configuration
@EnableWebSecurity
class AppSecurityConfig {

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .csrf(Customizer { obj: CsrfConfigurer<HttpSecurity> -> obj.disable() })
            .authorizeHttpRequests { auth ->
                auth
                    .requestMatchers("/api/v1/restaurant/**").permitAll()
                    .requestMatchers("/api/v1/user/create").permitAll()
                    .requestMatchers("/api/v1/user/page").hasRole("USER")
                    .requestMatchers("/api/v1/admin/page").hasRole("ADMIN")
                    .anyRequest().authenticated()

            }
        return http.build()
    }
}