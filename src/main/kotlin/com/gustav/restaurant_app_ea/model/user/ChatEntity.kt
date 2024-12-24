package com.gustav.restaurant_app_ea.model.user

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime

@Document(collection = "chat_history")
data class ChatEntity(
    @Id val id: String? = null,
    var ownerId: String,
    var engagedUserId: String,
    var timestamp: LocalDateTime,
    var chatHistory: List<String>?

)
{
}