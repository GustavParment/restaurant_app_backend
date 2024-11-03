package com.gustav.restaurant_app_ea.controller

import com.gustav.restaurant_app_ea.model.RestaurantEntity
import com.gustav.restaurant_app_ea.repository.RestaurantRepository
import com.gustav.restaurant_app_ea.service.RestaurantService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/restaurant")
class RestaurantController(
    private val restaurantService: RestaurantService
)
{
    @GetMapping("/get-all")
    fun getRestaurantFromDb() : ResponseEntity<List<RestaurantEntity>>
    {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(restaurantService.list())
    }
}
//TODO - BYGGA KLART GET, POST, DELETE OCH SEN SKRIVA TESTER