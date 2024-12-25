package com.gustav.restaurant_app_ea.service.restaurant

import com.gustav.restaurant_app_ea.model.dto.restaurant.ReviewDto
import com.gustav.restaurant_app_ea.model.restaurant.Review
import org.bson.types.ObjectId
import java.util.*

interface ReviewService{
    fun addReviewToRestaurant(review: ReviewDto, restaurantId: String): Review
    fun getReview(restaurantId: ObjectId): List<Review>
}