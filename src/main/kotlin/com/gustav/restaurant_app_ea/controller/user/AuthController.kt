package com.gustav.restaurant_app_ea.controller.user

import com.gustav.restaurant_app_ea.model.dto.user.AuthenticationRequest
import com.gustav.restaurant_app_ea.model.dto.user.AuthenticationResponse
import com.gustav.restaurant_app_ea.security.jwt.AuthenticationService
import jakarta.servlet.http.Cookie
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping

import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/api/v1/auth")
class AuthController(
    private val authenticationService: AuthenticationService
) {
    fun `things to do in this class`(){
        TODO("FIXA ADMIN LOGIN ENDPOINT JUST NU KAN USER LOGGA IN PÅ ADMIN SIDA")
    }

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
            cookie.isHttpOnly = true //KAN VARA ETT ISSUE!
            cookie.secure = true
            cookie.path = "/"
            cookie.maxAge = 3600000
            cookie.setAttribute("SameSite", "None")

            response.addCookie(cookie)

            ResponseEntity
                .status(HttpStatus.OK)
                .build()

        } catch (e: Exception) {
            ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(null)
        }
    }

    @PostMapping("/logout")
    fun logout(
        response: HttpServletResponse
    ): ResponseEntity<Void> {
        val cookie = Cookie("accessToken", null)
        cookie.isHttpOnly = true
        cookie.secure = true
        cookie.path = "/"
        cookie.maxAge = 0
        cookie.setAttribute("SameSite", "None")


        response.addCookie(cookie)

        return ResponseEntity.noContent().build()
    }
}
    //FOR DEBUGNING
    @GetMapping("/who-am-i")
    fun checkedLoggedInUser(request: HttpServletRequest): ResponseEntity<String> {
        println("Headers received:")
        request.headerNames.toList().forEach { headerName ->
            println("$headerName: ${request.getHeader(headerName)}")
        }

        val authentication: Authentication? = SecurityContextHolder.getContext().authentication
        println("---who-am-i---")
        println(authentication)

        return if (authentication != null && authentication.isAuthenticated) {

            val authorities = authentication.authorities.joinToString(", ") { it.authority }
            val username = authentication.name

            println("User is authenticated: $username with authorities: $authorities")
            ResponseEntity.ok("Logged in user: $username with authorities: $authorities")
        } else {
            println("User is not authenticated")
            ResponseEntity.status(401).body("User is not authenticated")
        }
    }




