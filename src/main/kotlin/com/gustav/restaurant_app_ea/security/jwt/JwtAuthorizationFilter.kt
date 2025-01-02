package com.gustav.restaurant_app_ea.security.jwt


import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class JwtAuthorizationFilter(
    private val userDetailsService: UserDetailsService,
    private val tokenService: TokenService
) : OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {

        val token = extractTokenFromRequest(request)

        if (!token.isNullOrEmpty()) {
            try {
                val username: String = tokenService.extractUsername(token)

                if (SecurityContextHolder.getContext().authentication == null) {
                    val userDetails: UserDetails = userDetailsService.loadUserByUsername(username)

                    if (tokenService.isTokenValid(token, userDetails)) {
                        val authToken = UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.authorities
                        )
                        authToken.details = WebAuthenticationDetailsSource().buildDetails(request)
                        SecurityContextHolder.getContext().authentication = authToken
                    }
                }
            } catch (ex: Exception) {
                response.writer.write(
                    """{"error": "Authorization error: ${ex.message ?: "unknown error"}"}"""
                )
            }
        }

        filterChain.doFilter(request, response)
    }

    private fun extractTokenFromRequest(request: HttpServletRequest): String? {
        val authorizationHeader = request.getHeader("Authorization")
        if (!authorizationHeader.isNullOrEmpty() && authorizationHeader.startsWith("Bearer ")) {
            println("Found token in Authorization header")
            return authorizationHeader.substringAfter("Bearer ")
        }

        val cookies = request.cookies


        return cookies?.firstOrNull { it.name == "accessToken" }?.value
    }

}
