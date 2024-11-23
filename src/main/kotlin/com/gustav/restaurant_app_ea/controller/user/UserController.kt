package com.gustav.restaurant_app_ea.controller.user

import com.gustav.restaurant_app_ea.config.exceptionhandling.UserAlreadyExistsException
import com.gustav.restaurant_app_ea.config.exceptionhandling.UserNotFoundException
import com.gustav.restaurant_app_ea.model.user.UserEntity
import com.gustav.restaurant_app_ea.model.dto.user.UserDto
import com.gustav.restaurant_app_ea.model.user.MatchEntity
import com.gustav.restaurant_app_ea.repository.user.UserRepository
import com.gustav.restaurant_app_ea.service.user.MatchService
import com.gustav.restaurant_app_ea.service.user.UserService
import io.github.resilience4j.ratelimiter.annotation.RateLimiter
import org.apache.catalina.User
import org.bson.types.ObjectId
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@Suppress("UNREACHABLE_CODE")
@RestController
@RequestMapping("/api/v1/user")
class UserController(
    private val userService: UserService,
    private val matchService: MatchService

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
        TODO("""
                -Skriva Klart metoden
                -Skriva tester för alla endpoints 6/15
                -Se över Felhantering
            """.trimIndent())
    }

    @RateLimiter(name = "rateLimiter")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    @GetMapping("/all")
    fun getAll(): ResponseEntity<List<UserEntity>>{
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(userService.list())

    }

    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    @GetMapping("/{id}")
    fun getById(@PathVariable("id") id: ObjectId): ResponseEntity<Any> {
        return try {
            ResponseEntity
                .status(HttpStatus.OK)
                .body(userService.findById(id))
        }catch (e : UserNotFoundException) {
            val errorResponse = mapOf(
                "error" to  "User Not Found",
                "message" to e.message,
                "status" to HttpStatus.NOT_FOUND.value()
            )
            return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(errorResponse)
        }

    }

    @RateLimiter(name = "rateLimiter")
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/{userId1}/match/{userId2}")
    fun createMatch(
        @PathVariable userId1: ObjectId,
        @PathVariable userId2: ObjectId
    ) : ResponseEntity<MatchEntity>
    {
        return try {
           ResponseEntity
               .status(HttpStatus.CREATED)
               .body(matchService.createMatch(userId1,userId2))
        }catch (e:Exception){
            ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(null)
        }
    }

    @RateLimiter(name = "rateLimiter")
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/{userId1}/{userId2}")
    fun getMatch(
        @PathVariable userId1: ObjectId,
        @PathVariable userId2: ObjectId
    ): ResponseEntity<List<MatchEntity>>
    {
        return try {
            ResponseEntity
                .status(HttpStatus.OK)
                .body(matchService.getMatch(userId1,userId2))
        }catch (e: Exception){
            ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(null)
        }
    }

}