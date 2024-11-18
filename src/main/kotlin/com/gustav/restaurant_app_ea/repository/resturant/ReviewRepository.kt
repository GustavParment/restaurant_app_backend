package com.gustav.restaurant_app_ea.repository.resturant

import com.gustav.restaurant_app_ea.model.restaurant.Review
import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface ReviewRepository: MongoRepository<Review, ObjectId> {
    fun findAllByRestaurantId(restaurantId: ObjectId): List<Review>
}