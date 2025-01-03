package com.gustav.restaurant_app_ea.controller.user

import com.gustav.restaurant_app_ea.model.dto.user.UserDto
import com.gustav.restaurant_app_ea.service.user.UserService
import io.github.resilience4j.ratelimiter.annotation.RateLimiter
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/admin")
class AdminController(
    private val userService: UserService,
) {


    @RateLimiter(name = "RateLimiter")
    @PostMapping("/create")
    fun createAdmin(
                    @RequestBody admin: UserDto)
    : ResponseEntity<Any>
    {
        return try {
            ResponseEntity
                .status(HttpStatus.CREATED)
                .body(userService.createAdmin(admin))

        } catch (e:Exception){
            ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(e.message)
        }

    }

    @GetMapping("/test")
    fun test()
    : ResponseEntity<Any>{
        return ResponseEntity.status(HttpStatus.OK).body("Hi SUPER_ADMIN")
    }
}