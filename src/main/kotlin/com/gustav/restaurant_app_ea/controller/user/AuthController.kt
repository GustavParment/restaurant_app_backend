package com.gustav.restaurant_app_ea.controller.user

import com.gustav.restaurant_app_ea.model.dto.user.AuthenticationRequest
import com.gustav.restaurant_app_ea.model.dto.user.AuthenticationResponse
import com.gustav.restaurant_app_ea.security.jwt.AuthenticationService
import jakarta.servlet.http.Cookie
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping


import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/api/v1/auth")
class AuthController(
    private val authenticationService: AuthenticationService
) {

    @PostMapping("/login")
    fun login(
      @RequestBody dtoRequest: AuthenticationRequest,
      response: HttpServletResponse
    ): ResponseEntity<AuthenticationResponse>
    {
        return try {
            val authResponse: AuthenticationResponse =
                authenticationService.authentication(dtoRequest)

            val cookie = Cookie("accessToken", authResponse.accessToken)
            cookie.isHttpOnly = true
            cookie.secure = true
            cookie.path = "/"
            cookie.maxAge = 360000

            response.addCookie(cookie)

            ResponseEntity
                .status(HttpStatus.OK)
                .body(authResponse)

        } catch (e: Exception) {
            ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(null)
        }
    }

    @PostMapping("/logout")
    fun logout(
        response : HttpServletResponse
    ): ResponseEntity<Void> {
        val cookie = Cookie("accessToken", null)
            .apply {
                path = "/"
                isHttpOnly = true
                maxAge = 0
            }
        response.addCookie(cookie)
        return ResponseEntity.noContent().build()
    }


}
