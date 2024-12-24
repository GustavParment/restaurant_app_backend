package com.gustav.restaurant_app_ea.service.user

import com.gustav.restaurant_app_ea.model.dto.user.ChatDto
import com.gustav.restaurant_app_ea.model.user.ChatEntity

interface ChatService {
    fun saveChat(chatDto: ChatDto) : ChatEntity
    fun getAllChatForUser(userId: String): List<ChatDto>?
    fun deleteChat(chatId: String)
    fun getChatSession(ownerId: String, engagedId: String): List<ChatDto>?
}