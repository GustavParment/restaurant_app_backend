package com.gustav.restaurant_app_ea.controller.restaurant

import com.gustav.restaurant_app_ea.model.restaurant.RestaurantEntity
import com.gustav.restaurant_app_ea.model.dto.restaurant.RestaurantDto
import com.gustav.restaurant_app_ea.service.restaurant.RestaurantService
import com.gustav.restaurant_app_ea.toDto
import com.gustav.restaurant_app_ea.toRestaurantListDto
import org.bson.types.ObjectId
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/restaurant")
class RestaurantController(
    private val restaurantService: RestaurantService
) {

    @GetMapping("/{id}")
    fun getRestaurantById (
        @PathVariable id: String,

    ) : ResponseEntity<RestaurantDto> {
        return try {
            val restaurant = restaurantService.getById(id)
            ResponseEntity.status(HttpStatus.OK).body(restaurant?.toDto())

        }catch (e : Exception){
            e.printStackTrace()
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build()
        }
    }

    @GetMapping("/all")
    fun getRestaurantFromDb(): ResponseEntity<Any> {
        return try {
            ResponseEntity
                .status(HttpStatus.OK)
                .body(restaurantService.list().toRestaurantListDto())

        } catch (e: Exception){
            e.printStackTrace()
            ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("An error occurred while fetching restaurants.")
        }
    }

    @PostMapping("/create")
    fun createRestaurant(@RequestBody restaurantDto: RestaurantDto): ResponseEntity<RestaurantEntity> {
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(restaurantService.save(restaurantDto))
    }

    @DeleteMapping("/remove/{id}")
    fun deleteRestaurantById(@PathVariable("id") id: String): ResponseEntity<Any> {
        return try {
            if (restaurantService.deleteById(id)) {
                ResponseEntity
                    .status(HttpStatus.NO_CONTENT)
                    .body("Restaurant deleted successfully.")

            }else {
                ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Restaurant with $id was not found in the database.")
            }
        }catch (e: Exception){
            ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Internal server error." + e.message)
        }

    }

}

