package com.gustav.restaurant_app_ea.repository.user

import com.gustav.restaurant_app_ea.model.user.ChatEntity
import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface ChatRepository: MongoRepository<ChatEntity,String> {
}