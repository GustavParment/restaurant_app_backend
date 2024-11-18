package com.gustav.restaurant_app_ea.model.dto.restaurant

import org.bson.types.ObjectId
import java.time.LocalDateTime

data class ReviewDto(
    val restaurantId: ObjectId,
    val userId: ObjectId,
    val userName: String,
    val rating: Int,
    val comment: String,
    ) {}