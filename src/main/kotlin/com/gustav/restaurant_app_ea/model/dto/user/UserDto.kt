package com.gustav.restaurant_app_ea.model.dto.user

import org.bson.types.ObjectId


data class UserDto(
    var id: String?,
    var username:String,
    var password:String?,
    val email:String,
    val firstName: String,
    val lastName:String,
    val birthday:String,
    var profile: List<UserProfileDto>? = emptyList(),
    var matchList: List<ObjectId>?


    )
{
}
data class UserProfileDto(
    var avatar: String,
    var bio: String,
    var favoriteFood: List<String>,
    var hobbies: List<String>
    )

data class UserHobbyInputDto(
    var hobbies: List<String>
)

data class UserFavoriteFoodInputDto(
    var favoriteFood: List<String>
)