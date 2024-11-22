package com.gustav.restaurant_app_ea.controller.user

import com.fasterxml.jackson.databind.ObjectMapper
import com.gustav.restaurant_app_ea.RestaurantAppEaApplication
import com.gustav.restaurant_app_ea.authorities.Role
import com.gustav.restaurant_app_ea.config.StartupConfig
import com.gustav.restaurant_app_ea.model.dto.user.UserDto
import com.gustav.restaurant_app_ea.model.user.UserEntity
import com.gustav.restaurant_app_ea.security.SecurityConfig
import com.gustav.restaurant_app_ea.security.jwt.TokenService
import com.gustav.restaurant_app_ea.service.restaurant.RestaurantService
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

@SpringBootTest(classes = [RestaurantAppEaApplication::class])
@AutoConfigureMockMvc
class AdminControllerTest {

    @Autowired
    private lateinit var adminController: AdminController

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockBean
    private lateinit var userService: UserService

    @MockBean
    private lateinit var tokenService: TokenService

    @MockBean
    private lateinit var startupConfig: StartupConfig

    private lateinit var mapper: ObjectMapper

    @BeforeEach
    fun setUp() {
        mapper = ObjectMapper()
    }

    @Test
    @WithMockUser(username = "Super Admin", roles = ["SUPER_ADMIN"])
    fun`test if SUPER_ADMIN can create a new ADMIN and expect status 201`()
    {
        val newAdminUser = UserDto(
            username = "Admin",
            password = "admin",
            birthday = "1990-01-01",
            email = "admin@gustav.pl",
            firstName = "Admin",
            lastName = "Adminsson",
            profile = emptyList()
        )
        val createdAdminUser = UserEntity(
            id = ObjectId.get(),
            username = "Admin",
            password = "admin",
            email = "admin@gustav.pl",
            firstName = "Admin",
            lastName = "Adminsson",
            birthday = "1990-01-01",
            role = Role.ADMIN,
            profile = emptyList()
        )

        Mockito.`when`(userService.createAdmin(newAdminUser)).thenReturn(createdAdminUser)

        val result = mockMvc.perform(
            MockMvcRequestBuilders.post("/api/v1/admin/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(newAdminUser))
        )
            .andExpect(MockMvcResultMatchers.status().isCreated)
           .andReturn()


        Mockito.verify(userService).createAdmin(newAdminUser)

         assertEquals(201, result.response.status)


    }
    @Test
    @WithMockUser(username = "Benny", roles = ["USER"])
    fun `test if USER can create new ADMIN and expect status 403`(){
        val newAdminUser = UserDto(
            username = "Admin",
            password = "admin",
            birthday = "1990-01-01",
            email = "admin@gustav.pl",
            firstName = "Admin",
            lastName = "Adminsson",
            profile = emptyList()
        )


        val result = mockMvc.perform(
            MockMvcRequestBuilders.post("/api/v1/admin/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(newAdminUser))
        )
            .andExpect(MockMvcResultMatchers.status().isForbidden)
            .andReturn()

        assertEquals(403, result.response.status)

    }
}