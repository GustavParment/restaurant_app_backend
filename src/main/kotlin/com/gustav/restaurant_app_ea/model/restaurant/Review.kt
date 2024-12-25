package com.gustav.restaurant_app_ea.model.restaurant

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime


@Document(collection = "review")
data class Review(
    @Id val id: String? = null,
    val restaurantId: String,
    val userId: String,
    val userName: String,
    val rating: Int,
    val comment: String,
    val date: LocalDateTime
)
