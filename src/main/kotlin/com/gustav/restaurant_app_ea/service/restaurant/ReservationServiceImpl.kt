package com.gustav.restaurant_app_ea.service.restaurant

import com.gustav.restaurant_app_ea.config.exceptionhandling.UserNotFoundException
import com.gustav.restaurant_app_ea.model.dto.restaurant.ReservationDto
import com.gustav.restaurant_app_ea.model.restaurant.ReservationEntity
import com.gustav.restaurant_app_ea.repository.resturant.ReservationRepository
import com.gustav.restaurant_app_ea.repository.resturant.RestaurantRepository
import com.gustav.restaurant_app_ea.repository.user.UserRepository
import com.gustav.restaurant_app_ea.security.authorities.Role
import com.gustav.restaurant_app_ea.toEntity
import org.bson.types.ObjectId
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class ReservationServiceImpl(
    private val reservationRepository: ReservationRepository,
    private val userRepository: UserRepository,

): ReservationService {

    override fun creatReservation(reservation: ReservationDto): ReservationEntity
    {
        val user = userRepository.findById(reservation.userId)

        if (user.role != Role.USER) {
            throw RuntimeException("User does not have permission to create reservations")
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