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


@Configuration
@EnableWebSecurity
class SecurityConfig {

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
            .csrf{ it.disable() }
            .authorizeHttpRequests {
                it
                    .requestMatchers("/api/v1/auth/login").permitAll()
                    .requestMatchers("/api/v1/restaurant/**").permitAll()
                    .requestMatchers("/api/v1/user/signup").permitAll()
                    .requestMatchers("/api/v1/user/all").hasAnyRole("ADMIN", "SUPER_ADMIN")
                    .requestMatchers("/api/v1/admin/create").hasRole("SUPER_ADMIN")
                    .requestMatchers("/api/v1/user/{id}").hasAnyRole("SUPER_ADMIN", "ADMIN")
                    .requestMatchers("/api/v1/reservation/**").hasRole("USER")
                    .requestMatchers("/api/v1/review/**").hasRole("USER")
//                    .requestMatchers("/api/v1/test/user").hasRole("USER")
//                    .requestMatchers("/api/v1/test/admin").hasRole("ADMIN")
//                    .requestMatchers("/api/v1/test/super_admin").hasRole("SUPER_ADMIN")
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