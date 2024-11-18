package com.gustav.restaurant_app_ea.model.dto.restaurant

import com.gustav.restaurant_app_ea.model.restaurant.Review
import org.bson.types.ObjectId

data class RestaurantDto(
    val id: ObjectId? = ObjectId(),
    val name: String,
    val address: String,
    val phone: String,
    val email: String,
    val description: String,
    val image: String,
    val rating: String,
    val menu: List<String>,
    val review: List<Review>
) {

}