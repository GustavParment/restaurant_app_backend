package com.gustav.restaurant_app_ea.repository.resturant;

import com.gustav.restaurant_app_ea.model.restaurant.ReservationEntity
import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface ReservationRepository:MongoRepository<ReservationEntity,String> {
    fun findByUserId(userId: String): List<ReservationEntity>
}
