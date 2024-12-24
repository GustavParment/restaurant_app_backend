package com.gustav.restaurant_app_ea.repository.user

import com.gustav.restaurant_app_ea.model.user.ChatEntity
import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.mongodb.repository.Query
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface ChatRepository: MongoRepository<ChatEntity,String> {
    @Query("{ 'userId': ?0 }")
    fun findAllChatsForUser(userId: String): MutableList<ChatEntity>

    @Query("{ 'ownerId': ?0, 'engagedId': ?1 }")
    fun findChatSessionForUser(ownerId: String, engagedId: String): Optional<ChatEntity>
}