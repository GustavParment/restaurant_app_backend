package com.gustav.restaurant_app_ea.model.dto.user

import com.gustav.restaurant_app_ea.model.user.MatchStatus
import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import java.util.*

data class MatchEntityDto(
    @Id val id: ObjectId = ObjectId(),
    val userId1: String,
    val userId2: String,
    val matchStatus: MatchStatus,
    val matchDate: Date = Date(),
) {

}

enum class MatchStatusDto{
    WAITING, ACCEPTED, DECLINED,
}