package com.gustav.restaurant_app_ea.service

import com.gustav.restaurant_app_ea.model.UserEntity
import com.gustav.restaurant_app_ea.model.dto.UserDto
import org.bson.types.ObjectId


interface UserService {
    fun create(user: UserDto): UserEntity
    fun list(): List<UserEntity>
    fun findByUsername(username: String): UserEntity?
    fun findById(id: ObjectId): UserEntity?
}