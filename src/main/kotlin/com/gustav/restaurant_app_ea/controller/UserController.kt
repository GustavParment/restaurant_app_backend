package com.gustav.restaurant_app_ea.controller

import com.gustav.restaurant_app_ea.model.UserEntity
import com.gustav.restaurant_app_ea.model.dto.UserDto
import com.gustav.restaurant_app_ea.service.UserService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/user")
class UserController(
    val userService: UserService
) {

    @PostMapping("/create")
    fun  create(@RequestBody user: UserDto): ResponseEntity <Any> {
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body("User CREATED successfully: " + userService.create(user))

    }

    @GetMapping("/all")
    fun getAll(): ResponseEntity<List<UserEntity>>{
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(userService.list())
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/page")
    fun getPage(): ResponseEntity<String>{
        return ResponseEntity
            .status(HttpStatus.OK)
            .body("Hello User");
    }

}