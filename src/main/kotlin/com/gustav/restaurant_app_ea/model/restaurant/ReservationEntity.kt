package com.gustav.restaurant_app_ea.model.restaurant

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime
@Document(collection = "reservation")
data class ReservationEntity(
    @Id val id: ObjectId? = ObjectId(),
    val userId: String?,
    val restaurantId: ObjectId? = ObjectId(),
    val reservationDate: LocalDateTime,
    val guests: Int
) {
}