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
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.apache.catalina.User
import org.bson.types.ObjectId
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.context.SecurityContextHolder
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
                -Skriva tester för alla endpoints 8/10
                -Se över Felhantering
            """.trimIndent())
    }

    @RateLimiter(name = "rateLimiter")
    @GetMapping("/all")
    fun getAll(response: HttpServletResponse): ResponseEntity<List<UserEntity>>{
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(userService.list())

    }

    @RateLimiter(name = "rateLimiter")
    @GetMapping("/browse")
        fun getAllWithRoleUser(
        response: HttpServletResponse,
        @RequestParam(required = false, defaultValue = "0" )
        index: Int
        ): ResponseEntity<UserEntity>{
            println("Accessing endpoint")
        if(index < 0) {
            return ResponseEntity.badRequest().body(null)
        }

        val loggedInUser = SecurityContextHolder.getContext().authentication.name

        val users = userService.list().filter { user ->
            user
                .role
                .name
                .equals("USER", ignoreCase = true)
                    &&
                    !user.username.equals(loggedInUser, ignoreCase = true)
            }
            val userAtIndex = users.getOrNull(index)
            return ResponseEntity
                .status(HttpStatus.OK)
                .body(userAtIndex)
        }

    @RateLimiter(name = "rateLimiter")
    @DeleteMapping("/delete/{id}")
    fun deleteUser(
        @PathVariable id: String,
        response: HttpServletResponse
        ): ResponseEntity<Any> {
        return try {
            val user = userService.findById(id) ?: throw UserNotFoundException("User not found with $id")


            return ResponseEntity
                .status(HttpStatus.OK)
                .body("$user was successfully deleted" + userService.deleteUser(user))

        }catch (e : Exception){
            return ResponseEntity(HttpStatus.BAD_REQUEST)
        }
    }

    @RateLimiter(name = "rateLimiter")
    @PutMapping("/update/{id}")
    fun updateUser(
        @PathVariable id: String,
        @RequestBody userDto: UserDto,
        response: HttpServletResponse
    ): ResponseEntity<Any>
    {
       return try {
           println("DEBUGG: UPDATEPASSWORD-------------------------")
           return ResponseEntity
               .status(HttpStatus.ACCEPTED)
               .body(userService.updateUser(id,userDto))

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

    @GetMapping("/{id}")
    fun getById(@PathVariable("id") id: String): ResponseEntity<Any> {
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
        @PathVariable userId1: String,
        @PathVariable userId2: String
    ) : ResponseEntity<MatchEntity>
    {
        return try {
           ResponseEntity
               .status(HttpStatus.CREATED)
               .body(matchService.createMatch(userId1,userId2))
        }catch (e:Exception){
            ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(null)
        }
    }

    @RateLimiter(name = "rateLimiter")
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/{userId1}/{userId2}")
    fun getMatch(
        @PathVariable userId1: String,
        @PathVariable userId2: String
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