package com.gustav.restaurant_app_ea.service.user

import com.gustav.restaurant_app_ea.model.dto.user.ChatDto
import com.gustav.restaurant_app_ea.model.user.ChatEntity
import org.springframework.stereotype.Service

@Service
class ChatServiceImpl(): ChatService {
    override fun saveChat(chatDto: ChatDto) {
        TODO("Not yet implemented")
    }

    override fun getChatHistory(chatDto: ChatDto): List<String>? {
        TODO("Not yet implemented")
    }

    override fun deleteChat(chatId: String) {
        TODO("Not yet implemented")
    }

    override fun getAllChatsForUser(chatId: ChatEntity): List<ChatEntity>? {
        TODO("Not yet implemented")
    }
}