package com.gustav.restaurant_app_ea.controller.user

import com.gustav.restaurant_app_ea.config.exceptionhandling.UserAlreadyExistsException
import com.gustav.restaurant_app_ea.model.user.UserEntity
import com.gustav.restaurant_app_ea.model.dto.user.UserDto
import com.gustav.restaurant_app_ea.service.user.UserService
import org.bson.types.ObjectId
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/user")
class UserController(
   private val userService: UserService
) {

    @PostMapping("/signup")
    fun  create(@RequestBody user: UserDto): ResponseEntity <Any> {
        val userExists: Boolean =
            userService.findByUsername(user.username) != null

        if (userExists) {
            throw UserAlreadyExistsException("Username already exists try another username")
        }

        return try {
            ResponseEntity
                .status(HttpStatus.CREATED)
                .body("User CREATED successfully: " + userService.create(user))

        } catch (e: Exception) {
            throw Exception("Internal Server Error")
        }


    }

    @GetMapping("/all")
    fun getAll(): ResponseEntity<List<UserEntity>>{
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(userService.list())
        TODO("Fel hantering!")
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    fun getById(@PathVariable("id") id: ObjectId): ResponseEntity<UserEntity> {
        return ResponseEntity
            .status(HttpStatus.OK).body(userService.findById(id))

        TODO("Fel hantering och testa endpoints ")
    }





}