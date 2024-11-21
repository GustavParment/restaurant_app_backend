package com.gustav.restaurant_app_ea.model.dto.user


data class UserDto(
    val username:String,
    val password:String,
    val email:String,
    val firstName: String,
    val lastName:String,
    val birthday:String,
    val profile: List<UserProfileDto>? = emptyList()

)
{
}
data class UserProfileDto(
    val avatar: String,
    val bio: String,
    val favoriteFood: List<String>,
    val hobbies: List<String>
    )

data class UserHobbyInputDto(
    val hobbies: List<String>
)

data class UserFavoriteFoodInputDto(
    val favoriteFood: List<String>
)