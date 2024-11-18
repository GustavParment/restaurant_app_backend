package com.gustav.restaurant_app_ea.model.dto

import com.gustav.restaurant_app_ea.model.RestaurantEntity
import com.gustav.restaurant_app_ea.model.UserEntity
import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import java.time.LocalDateTime

data class ReservationDto(
    val userId: ObjectId,
    val restaurantId: ObjectId,
    val reservationDate: LocalDateTime,
    val guests: Int

)
{
}