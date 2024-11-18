package com.gustav.restaurant_app_ea.repository.user

import com.gustav.restaurant_app_ea.model.user.UserEntity
import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository: MongoRepository<UserEntity, ObjectId> {
    fun findByUsername(username: String): UserEntity?
}