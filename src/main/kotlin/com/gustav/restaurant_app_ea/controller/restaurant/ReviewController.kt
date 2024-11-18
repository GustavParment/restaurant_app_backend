package com.gustav.restaurant_app_ea.controller.restaurant

import com.gustav.restaurant_app_ea.model.dto.restaurant.ReviewDto
import com.gustav.restaurant_app_ea.repository.resturant.RestaurantRepository
import com.gustav.restaurant_app_ea.repository.resturant.ReviewRepository
import com.gustav.restaurant_app_ea.service.restaurant.ReviewService
import com.gustav.restaurant_app_ea.toDto
import com.gustav.restaurant_app_ea.toEntity
import org.bson.types.ObjectId
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/review")
class ReviewController(
    private val reviewService: ReviewService
)
{
    @PostMapping("/add")
    fun addReview(@RequestBody reviewDto: ReviewDto)
    : ResponseEntity<String>
    {
        return try {
            reviewService.addReviewToRestaurant(reviewDto, reviewDto.restaurantId)

            ResponseEntity
                .status(HttpStatus.CREATED)
                .body("Review has been added successfully")
        }catch (e: Exception) {
            ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error while adding review")
        }
    }

    @GetMapping("/{restaurantId}")
    fun getAllReviews(@PathVariable restaurantId: ObjectId)
    : ResponseEntity<List<ReviewDto>>
    {
        return try {
            val review = reviewService.getReview(restaurantId)
            val reviewDto = review.map { it.toDto() }

            ResponseEntity
                .status(HttpStatus.OK)
                .body(reviewDto)

        }catch (e: Exception){
            ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(emptyList())

        }
    }

}