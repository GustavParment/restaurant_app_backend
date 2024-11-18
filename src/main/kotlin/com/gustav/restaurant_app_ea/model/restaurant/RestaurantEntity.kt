package com.gustav.restaurant_app_ea.model.restaurant

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "restaurants")
data class RestaurantEntity(
    @Id val id: ObjectId? = ObjectId(),
    val name: String,
    val address: String,
    val phone: String,
    val email: String,
    val description: String,
    val image: String,
    val rating: String,
    val menu: List<String>,
    val review: List<Review>
)