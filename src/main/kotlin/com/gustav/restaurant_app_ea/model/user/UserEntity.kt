package com.gustav.restaurant_app_ea.model.user

import com.gustav.restaurant_app_ea.authorities.Role
import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "user")
data class UserEntity(
    @Id var id: ObjectId? = ObjectId(),
    val username: String,
    val password: String,
    val email:String,
    val firstName:String,
    val lastName:String,
    val birthday:String,
    val role: Role,
    val profile: List<UserProfile>? = emptyList()
    )
{
    fun toUser(){
        TODO("Fixa Macthings logik och Profil data")
    }
}

data class UserProfile(
    val avatar: String,
    val bio: String,
    val aboutMe: String,
    var hobbies: MutableList<String>

)
{
}