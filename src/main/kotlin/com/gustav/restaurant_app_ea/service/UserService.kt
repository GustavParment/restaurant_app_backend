package com.gustav.restaurant_app_ea.service

import com.gustav.restaurant_app_ea.model.UserEntity
import com.gustav.restaurant_app_ea.model.dto.UserDto
import org.springframework.stereotype.Service


interface UserService {
    fun create(user: UserDto): UserEntity
    fun list(): List<UserEntity>
}