package com.gustav.restaurant_app_ea.controller.restaurant

import com.gustav.restaurant_app_ea.model.ReservationEntity
import com.gustav.restaurant_app_ea.model.dto.ReservationDto
import com.gustav.restaurant_app_ea.service.ReservationService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/reservation")
class ReservationController(
    private val reservationService: ReservationService
)
{
    @PostMapping("/create")
    fun creatReservation(@RequestBody reservationDto: ReservationDto)
    : ResponseEntity<Any>
    {
        return try {
            val reservation: ReservationEntity = reservationService.creatReservation(
                userId = reservationDto.userId,
                restaurantId = reservationDto.restaurantId,
                date = reservationDto.reservationDate,
                guests = reservationDto.guests
            )
            ResponseEntity
                .status(HttpStatus.CREATED)
                .body(reservation)

        }catch (e: Exception){
            ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(e.message)
        }

    }
}