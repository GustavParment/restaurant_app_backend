package com.gustav.restaurant_app_ea.model.user

import com.gustav.restaurant_app_ea.security.authorities.Role
import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "user")
data class UserEntity(
    @Id var id: String? = null,
    @Indexed(unique = true) var username: String,
    var password: String,
    val email:String,
    val firstName:String,
    val lastName:String,
    val birthday:String,
    val role: Role,
    val profile: List<UserProfile>? = emptyList(),
    val matchListId: List<ObjectId>?= emptyList(),
    var likes: Int? = 0


    )
{

}

data class UserProfile(
    val avatar: String,
    val bio: String,
    var favoriteFood: MutableList<String>,
    var hobbies: MutableList<String>

)
{
}