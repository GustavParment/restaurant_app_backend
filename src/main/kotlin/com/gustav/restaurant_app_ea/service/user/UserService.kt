package com.gustav.restaurant_app_ea.service.user

import com.gustav.restaurant_app_ea.model.user.UserEntity
import com.gustav.restaurant_app_ea.model.dto.user.UserDto
import org.bson.types.ObjectId


interface UserService {
    fun create(user: UserDto): UserEntity
    fun list(): List<UserEntity>
    fun findByUsername(username: String): UserEntity?
    fun findById(id: ObjectId): UserEntity?
    fun createAdmin(user: UserDto): UserEntity
}