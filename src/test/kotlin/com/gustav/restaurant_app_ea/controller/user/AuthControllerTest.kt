package com.gustav.restaurant_app_ea.controller.user

import com.fasterxml.jackson.databind.ObjectMapper
import com.gustav.restaurant_app_ea.RestaurantAppEaApplication
import com.gustav.restaurant_app_ea.model.dto.user.AuthenticationRequest
import com.gustav.restaurant_app_ea.model.dto.user.AuthenticationResponse
import com.gustav.restaurant_app_ea.security.jwt.AuthenticationService
import com.gustav.restaurant_app_ea.security.jwt.TokenService
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.mockito.kotlin.any
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import kotlin.test.assertEquals

@SpringBootTest( classes = [RestaurantAppEaApplication::class])
@AutoConfigureMockMvc
class AuthControllerTest{
    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockBean
    private lateinit var authenticationService: AuthenticationService

    @MockBean
    private lateinit var tokenService: TokenService

    @Autowired
    private lateinit var objectMapper: ObjectMapper


    @Test
    fun`Test login with good credentials and expect status 200 `(){

        val authenticationRequest = AuthenticationRequest("Benny", "123")
        val authenticationResponse = AuthenticationResponse("access-token")

        Mockito.`when`(authenticationService.authentication(any())).thenReturn(authenticationResponse)

       val result = mockMvc.perform(
            MockMvcRequestBuilders.post("/api/v1/auth/login")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(authenticationRequest))
        )
            .andExpect(status().isOk)
            .andExpect(
                MockMvcResultMatchers
                    .jsonPath("$.accessToken")
                    .value("access-token")
            )
            .andExpect(
                MockMvcResultMatchers
                    .jsonPath("$.refreshToken")
                    .value("refresh-token")
            )
            .andReturn()

        assertEquals(200, result.response.status)
        assertEquals(authenticationResponse.accessToken, "access-token")
        assertEquals(authenticationRequest.username,"Benny")
    }

    @Test
    fun `Test login with bad credentials and expect status 401 `(){
        val authenticationRequest = AuthenticationRequest(
            "invalid-user",
            "wrong-password"
        )

        Mockito.`when`(
            authenticationService
                .authentication(any()))
            .thenThrow(RuntimeException("Bad credentials")
            )

        val result = mockMvc.perform(
            MockMvcRequestBuilders.post("/api/v1/auth/login")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(authenticationRequest))
        )
            .andExpect(status().isUnauthorized)
            .andExpect(
                MockMvcResultMatchers
                    .jsonPath("$.accessToken")
                    .value(""))
            .andExpect(
                MockMvcResultMatchers
                    .jsonPath("$.refreshToken")
                    .value("")
            )
            .andReturn()

        assertEquals(401, result.response.status)

    }

    @Test
    @WithMockUser(username = "valid-user", roles = ["USER"])
    fun `Test logout with good credentials and expect status 200 `(){
        val token = "valid-token"

        Mockito.doNothing().`when`(authenticationService).logout(token)

        val result = mockMvc.perform(
            MockMvcRequestBuilders.post("/api/v1/auth/logout")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(token))
                .header("Authorization", "Bearer $token")
        )
            .andExpect(status().isOk)
            .andExpect(MockMvcResultMatchers.content()
                .string("Successfully logout")
            )
            .andReturn()

        assertEquals(200, result.response.status)
    }

    @Test
    fun `Test logout with bad credentials and expect status 403 `(){
        val token = "invalid-token"

        Mockito
            .doThrow(RuntimeException("Internal Server Error"))
            .`when`(authenticationService)
            .logout(token)

        val result = mockMvc.perform(
            MockMvcRequestBuilders.post("/api/v1/auth/logout")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(token))

        )
            .andExpect(status().isForbidden)
            .andExpect(MockMvcResultMatchers.content()
                .string("")
            )
            .andReturn()

        assertEquals(403, result.response.status)
    }

}

