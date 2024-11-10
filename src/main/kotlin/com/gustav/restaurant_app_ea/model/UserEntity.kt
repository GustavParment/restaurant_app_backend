package com.gustav.restaurant_app_ea.model

import com.gustav.restaurant_app_ea.authorities.Role
import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "user")
data class UserEntity(
    @Id var id: ObjectId? = ObjectId(),
    val username: String,
    val password: String,
    val roles: Role
    ) {
}