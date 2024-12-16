package com.gustav.restaurant_app_ea.service.user

import com.gustav.restaurant_app_ea.model.dto.user.ChatDto
import com.gustav.restaurant_app_ea.model.user.ChatEntity

interface ChatService {
    fun saveChat(chatDto: ChatDto)
    fun getChatHistory(chatDto : ChatDto): List<String>?
    fun deleteChat(chatId: String)
    fun getAllChatsForUser(chatId: String): List<ChatEntity>?
}