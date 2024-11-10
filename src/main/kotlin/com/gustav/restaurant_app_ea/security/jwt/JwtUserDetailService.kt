package com.gustav.restaurant_app_ea.security.jwt

import com.gustav.restaurant_app_ea.model.UserEntity
import com.gustav.restaurant_app_ea.repository.UserRepository
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class JwtUserDetailsService(
    private val userRepository: UserRepository
) : UserDetailsService {
    override fun loadUserByUsername(username: String): UserDetails {
        val user: UserEntity = userRepository.findByUsername(username)
            ?: throw UsernameNotFoundException("User $username not found!")

        return User.builder()
            .username(user.username)
            .password(user.password)
            .roles(user.role.name)
            .build()
    }
}