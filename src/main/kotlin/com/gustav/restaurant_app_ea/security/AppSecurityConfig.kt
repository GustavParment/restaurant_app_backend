package com.gustav.restaurant_app_ea.security


import com.gustav.restaurant_app_ea.repository.user.UserRepository
import com.gustav.restaurant_app_ea.security.jwt.JwtAuthorizationFilter

import com.gustav.restaurant_app_ea.security.jwt.JwtUserDetailsService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource


@Configuration
@EnableWebSecurity
class SecurityConfig(
    private val corsConfig: UrlBasedCorsConfigurationSource
) {

    @Bean
    fun userDetailsService(userRepository: UserRepository): UserDetailsService =
        JwtUserDetailsService(userRepository)

    @Bean
    fun authenticationManager(config: AuthenticationConfiguration): AuthenticationManager =
        config.authenticationManager

    @Bean
    fun authenticationProvider(userRepository: UserRepository): AuthenticationProvider =
        DaoAuthenticationProvider()
            .also {
                it.setUserDetailsService(userDetailsService(userRepository))
                it.setPasswordEncoder(encoder())
            }
    @Bean
    fun securityFilterChain(
        http: HttpSecurity,
        jwtAuthorizationFilter: JwtAuthorizationFilter,
        authenticationProvider: AuthenticationProvider
    ): SecurityFilterChain
    {
        http
            .cors { it.configurationSource(corsConfig) }
            .authorizeHttpRequests {

                it
                    .requestMatchers("/api/v1/user/signup").permitAll()
                    .requestMatchers("/api/v1/auth/login").permitAll()
                    .requestMatchers("/api/v1/auth/logout").hasAnyRole("USER", "ADMIN", "SUPER_ADMIN")
                    .requestMatchers("/api/v1/user/{userId1}/{userId2}").hasAnyRole(
                        "USER", "ADMIN", "SUPER_ADMIN"
                    )
                    .requestMatchers("/api/v1/user/browse").hasAnyRole("USER", "SUPER_ADMIN", "ADMIN")
                    .requestMatchers("/api/v1/user/all").hasAnyRole("ADMIN", "SUPER_ADMIN")
                    .requestMatchers("/api/v1/user/{id}").hasAnyRole("SUPER_ADMIN", "ADMIN")
                    .requestMatchers("/api/v1/user/update/{id}").hasAnyRole("SUPER_ADMIN", "ADMIN")
                    .requestMatchers("/api/v1/user/delete/{id}").hasAnyRole("SUPER_ADMIN", "ADMIN")
                    .requestMatchers("/api/v1/admin/create").hasRole("SUPER_ADMIN")
                    .requestMatchers("/api/v1/reservation/**").hasAnyRole("USER", "SUPER_ADMIN", "ADMIN")
                    .requestMatchers("/api/v1/restaurant/**").hasAnyRole("USER", "SUPER_ADMIN", "ADMIN")
                    .requestMatchers("/api/v1/review/**").hasRole("USER")
                    .requestMatchers("/api/v1/chat/**").hasAnyRole("USER", "ADMIN", "SUPER_ADMIN")
                    .anyRequest().fullyAuthenticated()
            }
            .sessionManagement{
                it.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            }
            .authenticationProvider(authenticationProvider)
            .addFilterBefore(jwtAuthorizationFilter,
                UsernamePasswordAuthenticationFilter::class.java
            )

        return http.build()


    }

    @Bean
    fun encoder(): PasswordEncoder = BCryptPasswordEncoder()

}