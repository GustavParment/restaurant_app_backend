package com.gustav.restaurant_app_ea.service.restaurant

import com.gustav.restaurant_app_ea.model.dto.restaurant.ReviewDto
import com.gustav.restaurant_app_ea.model.restaurant.Review
import com.gustav.restaurant_app_ea.repository.resturant.ReviewRepository
import com.gustav.restaurant_app_ea.toEntity
import org.bson.types.ObjectId
import org.springframework.stereotype.Service

@Service
class ReviewServiceImpl(
    private val reviewRepository: ReviewRepository)
    : ReviewService
{
    override fun addReviewToRestaurant(review: ReviewDto, restaurantId: ObjectId): Review {
        val reviewEntity = review.toEntity();

        val savedReview = reviewRepository.save(
           reviewEntity
               .copy(restaurantId = restaurantId)
       )
        return reviewRepository.save(savedReview)
    }

    override fun getReview(restaurantId: ObjectId): List<Review> {
        TODO("Not yet implemented")
    }
}