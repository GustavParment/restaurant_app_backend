package com.gustav.restaurant_app_ea.model.dto.user


data class UserDto(
    val username:String,
    val password:String,
    val email:String,
    val firstName: String,
    val lastName:String,
    val birthday:String,

)
{
}