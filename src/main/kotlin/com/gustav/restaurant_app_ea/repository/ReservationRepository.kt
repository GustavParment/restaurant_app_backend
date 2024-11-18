package com.gustav.restaurant_app_ea.repository;

import com.gustav.restaurant_app_ea.model.ReservationEntity
import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface ReservationRepository:MongoRepository<ReservationEntity,ObjectId> {
}
