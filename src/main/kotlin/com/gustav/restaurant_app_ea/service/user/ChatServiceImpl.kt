package com.gustav.restaurant_app_ea.service.user

import com.fasterxml.jackson.databind.util.ExceptionUtil
import com.gustav.restaurant_app_ea.model.dto.user.ChatDto
import com.gustav.restaurant_app_ea.model.user.ChatEntity
import com.gustav.restaurant_app_ea.repository.user.ChatRepository
import com.gustav.restaurant_app_ea.toDto
import com.gustav.restaurant_app_ea.toEntity
import org.springframework.stereotype.Service

@Service
class ChatServiceImpl(
    private val chatRepository: ChatRepository
): ChatService {
    override fun saveChat(chatDto: ChatDto): ChatEntity {
        val chatEntity = chatDto.toEntity()
        chatRepository.save(chatEntity)

    return chatEntity;
    }

    override fun getAllChatForUser(userId: String): List<ChatDto>? {
        val chatEntity = chatRepository.findAllChatsForUser(userId)

        if (chatEntity != null) {
            return chatEntity.map { it.toDto() }
        }
        return null
    }



    override fun deleteChat(chatId: String) {
        val chatEntity = chatRepository.findById(chatId)
            .orElseThrow {
                Exception("Chat not found with id: $chatId")
            }
        chatRepository.delete(chatEntity)
    }

    override fun getChatSession(ownerId: String, engagedId: String): List<ChatDto>? {
        val chatEntity = chatRepository.findChatSessionForUser(ownerId, engagedId)
            .orElseThrow { Exception("Chat not found with id: $ownerId") }
        val chatDto = chatEntity.toDto()
        return listOf(chatDto)
    }
}