package com.gustav.restaurant_app_ea.model.dto

import com.gustav.restaurant_app_ea.authorities.Role
import org.bson.types.ObjectId


data class UserDto(
    val id:ObjectId? = ObjectId(),
    val username:String,
    val password:String,
    val roles: Role
)
{
}