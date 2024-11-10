package com.gustav.restaurant_app_ea.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/test")
class TestController {
    fun todos(){
        TODO("""
            -Testa Endpoints
            -Utveckla Endpoints
            -Döpa om Endpoint
        """.trimIndent())

    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin")
    fun getAdmin(): ResponseEntity<String> {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body("Hello Admin");
    }

    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @GetMapping("/super_admin")
    fun getSuperAdmin(): ResponseEntity<String> {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body("Hello Super Admin");
    }
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/user")
    fun getUser(): ResponseEntity<String>{
        return ResponseEntity
            .status(HttpStatus.OK)
            .body("Hello User");
    }
}