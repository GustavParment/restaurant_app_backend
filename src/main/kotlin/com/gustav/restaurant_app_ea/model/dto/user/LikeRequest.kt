package com.gustav.restaurant_app_ea.model.dto.user

data class LikeRequest(
    val requestingUserId: String,
    val likedUserId: String,
    val like: Int

) {
}

