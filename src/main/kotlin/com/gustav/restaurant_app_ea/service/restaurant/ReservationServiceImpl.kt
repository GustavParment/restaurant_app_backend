package com.gustav.restaurant_app_ea.service.restaurant

import com.gustav.restaurant_app_ea.config.exceptionhandling.UserNotFoundException
import com.gustav.restaurant_app_ea.model.dto.restaurant.ReservationDto
import com.gustav.restaurant_app_ea.model.restaurant.ReservationEntity
import com.gustav.restaurant_app_ea.repository.resturant.ReservationRepository
import com.gustav.restaurant_app_ea.repository.resturant.RestaurantRepository
import com.gustav.restaurant_app_ea.repository.user.UserRepository
import com.gustav.restaurant_app_ea.toEntity
import org.bson.types.ObjectId
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class ReservationServiceImpl(
    private val reservationRepository: ReservationRepository,
    private val userRepository: UserRepository,
    private val restaurantRepository: RestaurantRepository
): ReservationService {

    override fun creatReservation(reservation: ReservationDto): ReservationEntity
    {
        val user = userRepository.findById(reservation.userId)

        val restaurant = reservation.restaurantId?.let {
            restaurantRepository.findById(it)
                .orElseThrow { RuntimeException("Restaurant not found") }
        }



        return reservationRepository.save(reservation.toEntity())
    }

    override fun findAllByUserId(userId: String): List<ReservationEntity> {
      return reservationRepository.findByUserId(userId)
    }

    override fun deleteById(id: String) {
        reservationRepository.deleteById(id)
    }


}