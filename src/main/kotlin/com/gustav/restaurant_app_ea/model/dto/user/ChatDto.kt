package com.gustav.restaurant_app_ea.model.dto.user

import java.time.LocalDateTime

data class ChatDto(
    val ownerId: String,
    val engagedUserId: String,
    val message: List<String>?
) {
}