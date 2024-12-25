package com.gustav.restaurant_app_ea.service.restaurant

import com.gustav.restaurant_app_ea.config.exceptionhandling.UserNotFoundException
import com.gustav.restaurant_app_ea.model.restaurant.ReservationEntity
import com.gustav.restaurant_app_ea.repository.resturant.ReservationRepository
import com.gustav.restaurant_app_ea.repository.resturant.RestaurantRepository
import com.gustav.restaurant_app_ea.repository.user.UserRepository
import org.bson.types.ObjectId
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class ReservationServiceImpl(
    private val reservationRepository: ReservationRepository,
    private val userRepository: UserRepository,
    private val restaurantRepository: RestaurantRepository
): ReservationService {

    override fun creatReservation(restaurantId: String, userId: String, date: String, guests: Int): ReservationEntity
    {
        val user = userRepository.findById(userId) ?: throw UserNotFoundException(userId)

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

    override fun findAllByUserId(userId: String): List<ReservationEntity> {
      return reservationRepository.findByUserId(userId)
    }

    override fun deleteById(id: String) {
        reservationRepository.deleteById(id)
    }


}