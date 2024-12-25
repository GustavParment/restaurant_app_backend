package com.gustav.restaurant_app_ea.model.dto.restaurant

import org.bson.types.ObjectId
import java.time.LocalDateTime

data class ReservationDto(
    val userId: String,
    val restaurantId: String,
    val reservationDate: String,
    val guests: Int

)
{
}