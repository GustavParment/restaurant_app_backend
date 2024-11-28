package com.gustav.restaurant_app_ea.controller.user

import com.gustav.restaurant_app_ea.security.jwt.AuthenticationRequest
import com.gustav.restaurant_app_ea.security.jwt.AuthenticationResponse
import com.gustav.restaurant_app_ea.security.jwt.AuthenticationService
import jakarta.servlet.http.Cookie
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam


import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/api/v1/auth")
class AuthController(
    private val authenticationService: AuthenticationService
) {

    @PostMapping("/login")
    fun login(
        @RequestParam username: String,
        @RequestParam password: String,
        response: HttpServletResponse
    )
            : ResponseEntity<AuthenticationResponse>
    {
        return try {
            val authRequest = AuthenticationRequest(username, password)
            val authResponse: AuthenticationResponse =
                authenticationService.authentication(authRequest)

            val refreshTokenCookie = Cookie("refreshToken", authResponse.refreshToken).apply {
                isHttpOnly = true
                secure = true
                path = "/"
                maxAge = 7 * 24 * 60 * 60
            }
            response.addCookie(refreshTokenCookie)


            ResponseEntity
                .status(HttpStatus.OK)
                .body(AuthenticationResponse(authResponse.accessToken, ""))

        } catch (e: Exception) {
            ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(AuthenticationResponse("", ""))
        }
    }

    @PostMapping("/logout")
    fun logout(
        @RequestBody token: String,
        response: HttpServletResponse
    ): ResponseEntity<String> {
        return try {
            authenticationService.logout(token)

            val cookie = Cookie("refreshToken", "").apply {
                isHttpOnly = true
                secure = true
                path = "/"
                maxAge = 0
            }
            response.addCookie(cookie)

            ResponseEntity
                .status(HttpStatus.OK)
                .body("Successfully logged out")
        } catch (e: Exception) {
            ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(e.message ?: "Unknown error")
        }
    }



    @PostMapping("/refresh")
    fun refreshToken(
        request: HttpServletRequest
    ): ResponseEntity<String> {
        try {
            val refreshTokenCookie = request.cookies?.find { it.name == "refreshToken" }
            val refreshToken = refreshTokenCookie?.value

            if (refreshToken.isNullOrEmpty()) {
                return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("No refresh token found")
            }

            val newAccessToken = authenticationService.refreshAccessToken(refreshToken)

            return ResponseEntity
                .status(HttpStatus.OK)
                .body(newAccessToken)
        } catch (e: Exception) {
            return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body("Invalid or expired refresh token")
        }
        TODO("""
            FIXA COOKIES?
            BYGGA OM JWT?
        """.trimIndent())

    }

}
