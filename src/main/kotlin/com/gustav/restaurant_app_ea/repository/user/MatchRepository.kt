package com.gustav.restaurant_app_ea.repository.user

import com.gustav.restaurant_app_ea.model.user.MatchEntity
import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface MatchRepository: MongoRepository<MatchEntity, ObjectId> {
    fun findByUserId1OrUserId2(userId1: ObjectId, userId2 : ObjectId): List<MatchEntity>
}