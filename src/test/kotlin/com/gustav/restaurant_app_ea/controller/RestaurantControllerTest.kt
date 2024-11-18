package com.gustav.restaurant_app_ea.controller

import com.gustav.restaurant_app_ea.controller.restaurant.RestaurantController
import com.gustav.restaurant_app_ea.model.RestaurantEntity
import com.gustav.restaurant_app_ea.model.dto.RestaurantDto
import com.gustav.restaurant_app_ea.service.RestaurantService
import org.bson.types.ObjectId
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

class RestaurantControllerTest {
    @Mock
    private lateinit var restaurantService: RestaurantService

    @InjectMocks
    private lateinit var restaurantController: RestaurantController

    @BeforeEach
    fun setUp() {
        MockitoAnnotations.openMocks(this)

    }
    private fun getMockEntity(): RestaurantEntity {
        return RestaurantEntity(
            id = ObjectId(),
            name = "Test Restaurant",
            address = "Test Address",
            phone = "123456789",
            email = "test@example.com",
            description = "Test Description",
            image = "test-image-url",
            rating = "5.0",
            menu = listOf("Item1", "Item2")
        )

    }

    private fun getMockDto(): RestaurantDto {
        return RestaurantDto(
            id = ObjectId(),
            name = "Test Restaurant",
            address = "Test Address",
            phone = "123456789",
            email = "test@example.com",
            description = "Test Description",
            image = "test-image-url",
            rating = "5.0",
            menu = listOf("Item1", "Item2")
        )
    }

    private fun generateMockObjectId(): ObjectId {
        return ObjectId.get()
    }

    @Test
    fun `getRestaurantFromDb should return list of restaurants when restaurants exist`() {

        val restaurantList = listOf(getMockEntity())

        `when`(restaurantService.list()).thenReturn(restaurantList)

        val response: ResponseEntity<Any> = restaurantController.getRestaurantFromDb()

        assertEquals(HttpStatus.OK, response.statusCode)
        assertNotNull(response.body)
        assertEquals(restaurantList, response.body)
    }

    @Test
    fun `createRestaurang should create new restaurant and save to database`() {
        val mockDto = getMockDto()

        `when`(restaurantService.save(mockDto)).thenReturn(getMockEntity())

        val response : ResponseEntity<RestaurantEntity> = restaurantController.createRestaurant(mockDto)

        assertEquals(HttpStatus.CREATED, response.statusCode)
        assertNotNull(response.body)
        assertEquals(mockDto.name, response.body!!.name)
        assertEquals(mockDto.address, response.body!!.address)
        assertEquals(mockDto.phone, response.body!!.phone)

        verify(restaurantService).save(mockDto)
    }

    @Test
    fun `deleteRestaurant should return NO_CONTET when when restaurant is deleted `() {
        val restaurantMockId = generateMockObjectId()

        `when`(restaurantService.deleteById(restaurantMockId)).thenReturn(true)

        val response: ResponseEntity<Any> =
            restaurantController.deleteRestaurantById(restaurantMockId)

        assertEquals(HttpStatus.NO_CONTENT, response.statusCode)
        assertEquals("Restaurant deleted successfully.", response.body)
        verify(restaurantService).deleteById(restaurantMockId)

    }

    @Test
    fun `deleteRestaurantById should return INTERNAL_SERVER_ERROR on exception`(){
        val restaurantMockId = generateMockObjectId()

        `when`(restaurantService.deleteById(restaurantMockId))
            .thenThrow(RuntimeException("Unexpected error"))

        val response: ResponseEntity<Any> =
            restaurantController.deleteRestaurantById(restaurantMockId)

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.statusCode)
        assertEquals("Internal server error.Unexpected error", response.body)
        verify(restaurantService).deleteById(restaurantMockId)
    }
}
