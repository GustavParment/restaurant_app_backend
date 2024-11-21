package com.gustav.restaurant_app_ea.model.user

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.util.*

@Document(collection = "user match")
data class MatchEntity(
    @Id val id: ObjectId = ObjectId(),
    val userId1: ObjectId,
    val userId2: ObjectId,
    val matchStatus: MatchStatus,
    val matchDate: Date = Date(),
    val commonHobby: List<String>?,
    val commonFood: List<String>?,


    )
enum class MatchStatus{
    WAITING, ACCEPTED, DECLINED,
}