package com.gustav.restaurant_app_ea.controller.restaurant

import com.gustav.restaurant_app_ea.model.restaurant.ReservationEntity
import com.gustav.restaurant_app_ea.model.dto.restaurant.ReservationDto
import com.gustav.restaurant_app_ea.service.restaurant.ReservationService
import com.gustav.restaurant_app_ea.toDto
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

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
            val reservation = reservationService.creatReservation(reservationDto)
            ResponseEntity
                .status(HttpStatus.CREATED)
                .body(reservation.toDto())

        }catch (e: Exception){
            ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(e.message)
        }

    }

    @GetMapping("/all/{userId}")
    fun getAllReservations(@PathVariable userId: String): ResponseEntity<List<ReservationEntity>> {
        return try {
            val reservations = reservationService.findAllByUserId(userId)

            ResponseEntity.status(HttpStatus.OK).body(reservations)
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(emptyList())
        }
    }

    @DeleteMapping("/delete/{reservationId}")
    fun deleteReservation(@PathVariable reservationId: String): ResponseEntity<String> {
        return try {
            reservationService.deleteById(reservationId)
            ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .body("Reservation deleted successfully.")
        } catch (e: Exception) {
            ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body("Failed to delete reservation: ${e.message}")
        }
    }


}