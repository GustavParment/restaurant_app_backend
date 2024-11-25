package com.gustav.restaurant_app_ea.controller.user

import com.gustav.restaurant_app_ea.security.jwt.AuthenticationRequest
import com.gustav.restaurant_app_ea.security.jwt.AuthenticationResponse
import com.gustav.restaurant_app_ea.security.jwt.AuthenticationService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/auth")
class AuthController(
    private val authenticationService: AuthenticationService
) {

    @PostMapping("/login")
    fun login(
        @RequestParam username: String,
        @RequestParam password: String)
    : ResponseEntity<AuthenticationResponse>
    {
        return try {

            val authRequest = AuthenticationRequest(username,password)
            val authResponse: AuthenticationResponse =
                authenticationService.authentication(authRequest)

            ResponseEntity
                .status(HttpStatus.OK)
                .body(authResponse)
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                AuthenticationResponse("", "")
            )
        }
    }

    @PostMapping("/logout")
    fun logout(@RequestBody token: String)
    : ResponseEntity<String>
    {
        return try {
            authenticationService.logout(token)
            ResponseEntity
                .status(HttpStatus.OK)
                .body("Successfully logout")
        }catch (e: Exception){
            ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(e.message)
        }

    }


    @PostMapping("/refresh")
    fun refreshToken(@RequestBody refreshToken: String)
    : ResponseEntity<String>
    {
        return try {
            val newAccessToken = authenticationService.refreshAccessToken(refreshToken)
            ResponseEntity
                .status(HttpStatus.OK)
                .body(newAccessToken)
        } catch (
            e: Exception
        ){
            ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body("Invalid refresh token")
        }
    }
}
