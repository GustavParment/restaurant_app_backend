package com.gustav.restaurant_app_ea.security.jwt

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter


@Component
class JwtAuthenticationFilter(
    private val jwtUtil: JwtUtil,
    private val jwtUserDetailsService: JwtUserDetailsService
) : OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val authorizationHeader = request.getHeader("Authorization")

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            val jwt = authorizationHeader.substring(7)
            val username = jwtUtil.extractUsername(jwt)

            if (SecurityContextHolder.getContext().authentication == null) {
                val userDetails = jwtUserDetailsService.loadUserByUsername(username)

                if (jwtUtil.validateToken(jwt, userDetails.username)) {
                    val authToken = org.springframework.security.authentication.UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.authorities
                    )
                    authToken.details = WebAuthenticationDetailsSource().buildDetails(request)
                    SecurityContextHolder.getContext().authentication = authToken  // Sätt autentiseringen i kontexten
                }
            }
        }

        filterChain.doFilter(request, response)
    }
}
