package com.gustav.restaurant_app_ea.controller.user

import com.fasterxml.jackson.databind.ObjectMapper
import com.gustav.restaurant_app_ea.RestaurantAppEaApplication
import com.gustav.restaurant_app_ea.authorities.Role
import com.gustav.restaurant_app_ea.config.StartupConfig
import com.gustav.restaurant_app_ea.model.dto.user.UserDto
import com.gustav.restaurant_app_ea.model.user.UserEntity
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
    fun `test GET user by id should return 201`(){
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



}