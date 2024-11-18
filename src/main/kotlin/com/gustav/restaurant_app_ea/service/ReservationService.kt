package com.gustav.restaurant_app_ea.service

import com.gustav.restaurant_app_ea.model.ReservationEntity
import com.gustav.restaurant_app_ea.model.RestaurantEntity
import com.gustav.restaurant_app_ea.model.UserEntity
import com.gustav.restaurant_app_ea.model.dto.ReservationDto
import org.bson.types.ObjectId
import java.time.LocalDateTime
import java.util.*

interface ReservationService {
    fun creatReservation(
        restaurantId: ObjectId,
        userId: ObjectId,
        date: LocalDateTime,
        guests: Int
    ):ReservationEntity
}