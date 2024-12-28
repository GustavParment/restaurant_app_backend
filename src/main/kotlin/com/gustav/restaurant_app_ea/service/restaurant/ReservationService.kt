package com.gustav.restaurant_app_ea.service.restaurant

import com.gustav.restaurant_app_ea.model.dto.restaurant.ReservationDto
import com.gustav.restaurant_app_ea.model.restaurant.ReservationEntity

interface ReservationService {
    fun creatReservation(reservation: ReservationDto): ReservationEntity

    fun findAllByUserId(userId: String): List<ReservationEntity>
    fun deleteById(id: String)
}