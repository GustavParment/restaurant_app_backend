package com.gustav.restaurant_app_ea.controller.user

import com.fasterxml.jackson.databind.ObjectMapper
import com.gustav.restaurant_app_ea.RestaurantAppEaApplication
import com.gustav.restaurant_app_ea.authorities.Role
import com.gustav.restaurant_app_ea.config.StartupConfig
import com.gustav.restaurant_app_ea.config.exceptionhandling.UserNotFoundException
import com.gustav.restaurant_app_ea.model.dto.user.UserDto
import com.gustav.restaurant_app_ea.model.user.MatchEntity
import com.gustav.restaurant_app_ea.model.user.MatchStatus
import com.gustav.restaurant_app_ea.model.user.UserEntity
import com.gustav.restaurant_app_ea.model.user.UserProfile
import com.gustav.restaurant_app_ea.repository.user.UserRepository
import com.gustav.restaurant_app_ea.security.SecurityConfig
import com.gustav.restaurant_app_ea.security.jwt.TokenService
import com.gustav.restaurant_app_ea.service.restaurant.RestaurantService
import com.gustav.restaurant_app_ea.service.user.MatchService
import com.gustav.restaurant_app_ea.service.user.UserService
import io.jsonwebtoken.Claims
import org.bson.types.ObjectId
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import java.util.*
import kotlin.test.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.mockito.Mockito.*

@SpringBootTest(classes = [RestaurantAppEaApplication :: class])
@AutoConfigureMockMvc
class UserControllerTest{
    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockBean
    private lateinit var userRepository: UserRepository

    @MockBean
    private lateinit var matchService: MatchService

    @MockBean
    private lateinit var startupConfig: StartupConfig

    @MockBean
    private lateinit var userService: UserService

    @Autowired
    private lateinit var mapper: ObjectMapper

    @BeforeEach
    fun setUp() {
        mapper = ObjectMapper()

    }

    @Test
    fun `test signup should return 201`() {
        val mockDto = UserDto(
            username = "Benny",
            password = "123",
            firstName = "Benny",
            lastName = "Benny",
            email = "benny@gbennyson.se",
            birthday = "1337",
        )

        val mockUser = UserEntity(
            username = "Benny",
            password = "123",
            firstName = "Benny",
            lastName = "Benny",
            email = "benny@gbennyson.se",
            birthday = "1337",
            role = Role.USER
        )

        Mockito.`when`(userService.create(mockDto)).thenReturn(mockUser)

        val result = mockMvc.perform(
            MockMvcRequestBuilders.post("/api/v1/user/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(mockDto))
        )
            .andExpect(MockMvcResultMatchers.status().isCreated)
            .andReturn()
        assertEquals(201, result.response.status)
    }


    @Test
    @WithMockUser(username = "Admin", roles = ["ADMIN"])
    fun `test GET all users as ADMIN should return 200`(){
        val mockUser = UserEntity(
            username = "Benny",
            password = "123",
            email = "benny@gustav.pl",
            birthday = "1337",
            firstName = "Benny",
            lastName = "Bennysson",
            role = Role.USER

        )

        val userList = listOf(mockUser)

        Mockito.`when`(userRepository.findAll()).thenReturn(userList)

        val result = mockMvc.perform(
            MockMvcRequestBuilders.get("/api/v1/user/all")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(mockUser))
        )
        .andExpect(MockMvcResultMatchers.status().isOk)
        .andReturn()

        assertEquals(200, result.response.status)

    }

    @Test
    @WithMockUser(username = "Benny", roles = ["USER"])
    fun `test GET all users as USER should return 403`(){
        val mockUser = UserEntity(
            username = "Benny",
            password = "123",
            email = "benny@gustav.pl",
            birthday = "1337",
            firstName = "Benny",
            lastName = "Bennysson",
            role = Role.USER

        )

        val userList = listOf(mockUser)

        Mockito.`when`(userRepository.findAll()).thenReturn(userList)

        val result = mockMvc.perform(
            MockMvcRequestBuilders.get("/api/v1/user/all")
            .contentType(MediaType.APPLICATION_JSON)
            .content(mapper.writeValueAsString(mockUser))
        )
            .andExpect(MockMvcResultMatchers.status().isForbidden)
            .andReturn()

        assertEquals(403, result.response.status)
    }

    @Test
    @WithMockUser(username = "Admin", roles = ["ADMIN"])
    fun `test GET user by id with role ADMIN should return 201`(){
        val mockUser = UserEntity(
            username = "Benny",
            password = "123",
            email = "benny@bennysson.com",
            birthday = "1337",
            firstName = "Benny",
            lastName = "Benny",
            role = Role.USER
        )

        val userId = ObjectId()

        Mockito.`when`(userService.findById(userId)).thenReturn(mockUser)

        val result = mockMvc.perform(
            MockMvcRequestBuilders.get("/api/v1/user/$userId")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(mockUser))
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andReturn()

        assertEquals(200, result.response.status)
    }

    @Test
    @WithMockUser(username = "Benny", roles = ["USER"])
    fun `test GET user by id with role USER should return 403`(){
        val userId = ObjectId()

        Mockito.`when`(userService.findById(userId)).thenReturn(null)
        val result = mockMvc.perform(
            MockMvcRequestBuilders.get("/api/v1/user/$userId")
            .contentType(MediaType.APPLICATION_JSON)

        )
            .andExpect(MockMvcResultMatchers.status().isForbidden)
            .andReturn()

        assertEquals(403, result.response.status)


    }

    @Test
    @WithMockUser(username = "Admin", roles = ["ADMIN"])
    fun `test get user by id when user is non existing should return 404 when user is not found`() {

        val userId = ObjectId.get()
        `when`(userService.findById(userId)).thenThrow(UserNotFoundException("User Not Found"))

       val result = mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/user/{id}", userId.toString()))
            .andExpect(MockMvcResultMatchers.status().isNotFound)  // Förvänta en 404-statuskod
            .andExpect(MockMvcResultMatchers.jsonPath("$.error").value("User Not Found"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(404))
            .andReturn()

        assertEquals(404, result.response.status)
    }

    @Test
    @WithMockUser(username = "Benny", roles = ["USER"])
    fun `test create match between users should return 201 when common hobbies & food`(){
        val userProfile1 = UserProfile(
            avatar = "img.jpg",
            bio = "a handsome guy",
            favoriteFood = mutableListOf("Pizza", "Sushi"),
            hobbies = mutableListOf("Reading", "Swimming")
        )
        val mockUser1 = UserEntity(
            id = ObjectId(),
            username = "Benny",
            password = "123",
            email = "benny@gustav.pl",
            birthday = "1337",
            firstName = "Benny",
            lastName = "Bennysson",
            role = Role.USER,
            profile = listOf(userProfile1),
            matchListId = emptyList()

            )

        val userProfile2 = UserProfile(
            avatar = "img.jpg",
            bio = "a handsome girl",
            favoriteFood = mutableListOf("Spaghetti", "Sushi"),
            hobbies = mutableListOf("Reading", "Climbing")

        )
        val mockUser2 = UserEntity(
            id = ObjectId(),
            username = "Frida",
            password = "123",
            email = "frida@frida.com",
            birthday = "1337",
            firstName = "Frida",
            lastName = "Fridasson",
            role = Role.USER,
            profile = listOf(userProfile2),
            matchListId = emptyList()

        )

        val matchEntity = MatchEntity(
            userId1 = mockUser1.id,
            userId2 = mockUser2.id,
            matchStatus = MatchStatus.WAITING
        )

        `when`(matchService.createMatch(mockUser1.id, mockUser2.id)).thenReturn(matchEntity)

        val result = mockMvc.perform(
            MockMvcRequestBuilders.post("/api/v1/user/{userId1}/match/{userId2}", mockUser1.id, mockUser2.id)
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(MockMvcResultMatchers.status().isCreated)
            .andReturn()
        println("Response Body: ${result.response.contentAsString}")

        assertEquals(201, result.response.status)

    }

    @Test
    @WithMockUser(username = "Frida", roles = ["USER"])
    fun `test create match between users should return 404 if no common hobbies & food`(){

        val userProfile1 = UserProfile(
            avatar = "img.jpg",
            bio = "a handsome guy",
            favoriteFood = mutableListOf("Pizza", "Sushi"),
            hobbies = mutableListOf("Reading", "Swimming")
        )

        val user1 = UserEntity(
            id = ObjectId(),
            username = "user1",
            password = "password1",
            email = "user1@example.com",
            firstName = "User1",
            lastName = "Test",
            birthday = "1990-01-01",
            role = Role.USER
        )

        val user2 = UserEntity(
            id = ObjectId(),
            username = "user2",
            password = "password2",
            email = "user2@example.com",
            firstName = "User2",
            lastName = "Test",
            birthday = "1992-02-02",
            role = Role.USER
        )

        val userProfile2 = UserProfile(
            avatar = "img.jpg",
            bio = "a handsome girl",
            favoriteFood = mutableListOf("Spaghetti"),
            hobbies = mutableListOf("Reading", "Climbing")

        )

        val result = mockMvc.perform(
            MockMvcRequestBuilders.post()
        )

        TODO("""
            Skriva klart Tester
        """.trimIndent())


    }







}