package com.gustav.restaurant_app_ea.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/admin")
class AdminController {

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/page")
    fun getPage(): ResponseEntity<String> {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body("Hello Admin");
    }
}