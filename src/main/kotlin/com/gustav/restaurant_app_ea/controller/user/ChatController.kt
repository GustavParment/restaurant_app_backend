package com.gustav.restaurant_app_ea.controller.user

import com.gustav.restaurant_app_ea.model.dto.user.ChatDto
import com.gustav.restaurant_app_ea.model.user.ChatEntity
import com.gustav.restaurant_app_ea.service.user.ChatService
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
    @GetMapping("/all/{id}")
    fun getAllChatForUser(
        response: HttpServletResponse,
        @PathVariable id: String,

        ): ResponseEntity<List<ChatEntity>>
    {
        return try {
            val allChatForUser = chatService.getAllChatsForUser(id)

            ResponseEntity.status(HttpStatus.OK).body(allChatForUser)

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