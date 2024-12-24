package com.gustav.restaurant_app_ea.controller.user

import com.gustav.restaurant_app_ea.model.dto.user.AuthenticationRequest
import com.gustav.restaurant_app_ea.model.dto.user.AuthenticationResponse
import com.gustav.restaurant_app_ea.security.jwt.AuthenticationService
import com.gustav.restaurant_app_ea.service.user.UserService
import jakarta.servlet.http.Cookie
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/api/v1/auth")
class AuthController(
    private val authenticationService: AuthenticationService,
    private val userService: UserService
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

//    //FOR DEBUGNING
//    @GetMapping("/who-am-i")
//    fun checkedLoggedInUser(request: HttpServletRequest): ResponseEntity<String> {
//        println("Headers received:")
//        request.headerNames.toList().forEach { headerName ->
//            println("$headerName: ${request.getHeader(headerName)}")
//        }
//
//        val authentication: Authentication? = SecurityContextHolder.getContext().authentication
//        println("---who-am-i---")
//        println(authentication)
//
//        return if (authentication != null && authentication.isAuthenticated) {
//
//            val authorities = authentication.authorities.joinToString(", ") { it.authority }
//            val username = authentication.name
//
//            println("User is authenticated: $username with authorities: $authorities")
//            ResponseEntity.ok("Logged in user: $username with authorities: $authorities")
//        } else {
//            println("User is not authenticated")
//            ResponseEntity.status(401).body("User is not authenticated")
//        }
//    }
    @GetMapping("/who-am-i")
    fun checkedLoggedInUser(request: HttpServletRequest): ResponseEntity<Any> {
        val authentication: Authentication? = SecurityContextHolder.getContext().authentication
        return if (authentication != null && authentication.isAuthenticated) {
            val username = authentication.name
            val authorities = authentication.authorities.joinToString(", ") { it.authority }
            val userDetails = mapOf("username" to username, "authorities" to authorities)
            println(userDetails)
            ResponseEntity.status(HttpStatus.OK).body(userDetails)
        } else {
            ResponseEntity.status(401).body("User is not authenticated")
        }
    }

    @GetMapping("/current-user")
    fun getCurrentUser(): ResponseEntity<Any> {
        val authentication = SecurityContextHolder.getContext().authentication
        if (authentication != null && authentication.isAuthenticated) {
            val username = authentication.name
            val user = userService.findByUsername(username)
            return if (user != null) ResponseEntity.ok(user) else ResponseEntity.notFound().build()
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User is not authenticated")
    }

//    @GetMapping("/current-user")
//    fun getCurrentUser(request: HttpServletRequest): ResponseEntity<Any> {
//        val authentication = SecurityContextHolder.getContext().authentication
//        return if (authentication != null && authentication.isAuthenticated) {
//            // Assuming CustomUserDetails is your user details implementation
//            val userDetails = authentication.principal as CustomUserDetails
//            val userInfo = mapOf("id" to userDetails.id, "username" to userDetails.username)
//            ResponseEntity.ok(userInfo)
//        } else {
//            ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User is not authenticated")
//        }
//    }

}




