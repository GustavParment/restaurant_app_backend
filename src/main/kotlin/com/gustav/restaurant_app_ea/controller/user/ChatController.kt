package com.gustav.restaurant_app_ea.controller.user

import com.gustav.restaurant_app_ea.model.dto.user.ChatDto
import com.gustav.restaurant_app_ea.model.user.ChatEntity
import com.gustav.restaurant_app_ea.service.user.ChatService
import com.gustav.restaurant_app_ea.toEntity
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/chat")
class ChatController(
    private val chatService: ChatService
)
{
    @GetMapping("/session/{ownerId}/{engagedId}")
    fun getChatSession(
        response: HttpServletResponse,
        @PathVariable ownerId: String,
        @PathVariable engagedId: String,

        ): ResponseEntity<List<ChatDto>>
    {
        return try {
            val chatSession = chatService.getChatSession(ownerId, engagedId)

            ResponseEntity
                .status(HttpStatus.OK)
                .body(chatSession)

        }catch (e:Exception){
            ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(null)
        }
    }

    @PostMapping("/new-session/{ownerId}/{engagedId}")
    fun newChatSession(
        @RequestBody chat: ChatDto,
        response: HttpServletResponse,
    ) : ResponseEntity<ChatEntity>
    {
    return try {
        val chatEntity = chatService.saveChat(chat);

        ResponseEntity.status(HttpStatus.CREATED).body(chatEntity)

    }catch (e:Exception){
        ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null)
    }
    }

    @PostMapping("/send-chat")
    fun sendChatToUser(
        @RequestBody chatDto: ChatDto,
        response: HttpServletResponse
    ):ResponseEntity<String>
    {
        return try {
            chatService.saveChat(chatDto)
            ResponseEntity
                .status(HttpStatus.CREATED)
                .body(chatDto.message.toString())

        }catch (e : Exception){
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null)
        }
    }

}