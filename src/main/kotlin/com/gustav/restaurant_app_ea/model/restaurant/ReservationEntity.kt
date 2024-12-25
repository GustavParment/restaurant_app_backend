package com.gustav.restaurant_app_ea.model.restaurant

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime
@Document(collection = "reservation")
data class ReservationEntity(
    @Id val id: String? = null,
    val userId: String?,
    val restaurantId: String?,
    val reservationDate: String?,
    val guests: Int
) {
}