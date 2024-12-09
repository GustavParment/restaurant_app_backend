package com.gustav.restaurant_app_ea.service.restaurant

import com.gustav.restaurant_app_ea.model.restaurant.ReservationEntity
import org.bson.types.ObjectId
import java.time.LocalDateTime

interface ReservationService {
    fun creatReservation(
        restaurantId: ObjectId,
        userId: String,
        date: LocalDateTime,
        guests: Int
    ): ReservationEntity
}