package com.gustav.restaurant_app_ea.service.impl

import com.gustav.restaurant_app_ea.model.restaurant.ReservationEntity
import com.gustav.restaurant_app_ea.repository.resturant.ReservationRepository
import com.gustav.restaurant_app_ea.repository.resturant.RestaurantRepository
import com.gustav.restaurant_app_ea.repository.user.UserRepository
import com.gustav.restaurant_app_ea.service.restaurant.ReservationService
import org.bson.types.ObjectId
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class ReservationServiceImpl(
    private val reservationRepository: ReservationRepository,
    private val userRepository: UserRepository,
    private val restaurantRepository: RestaurantRepository
): ReservationService {

    override fun creatReservation(
        restaurantId: ObjectId,
        userId: ObjectId,
        date: LocalDateTime,
        guests: Int
    ): ReservationEntity
    {
        val user = userRepository.findById(userId)
            .orElseThrow { RuntimeException("User not found") }

        val restaurant = restaurantRepository.findById(restaurantId)
            .orElseThrow { RuntimeException("Restaurant not found") }


        val reservation = ReservationEntity(
            userId = user.id,
            restaurantId = restaurant.id,
            reservationDate = date,
            guests = guests
        )
        return reservationRepository.save(reservation)
    }
}