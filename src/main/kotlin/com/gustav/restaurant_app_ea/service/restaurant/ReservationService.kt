package com.gustav.restaurant_app_ea.service.restaurant

import com.gustav.restaurant_app_ea.model.restaurant.ReservationEntity

interface ReservationService {
    fun creatReservation(
        restaurantId: String,
        userId: String,
        date: String,
        guests: Int
    ): ReservationEntity

    fun findAllByUserId(userId: String): List<ReservationEntity>
    fun deleteById(id: String)
}