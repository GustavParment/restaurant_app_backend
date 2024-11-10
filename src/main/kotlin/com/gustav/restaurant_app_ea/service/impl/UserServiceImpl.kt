package com.gustav.restaurant_app_ea.service.impl

import com.gustav.restaurant_app_ea.model.UserEntity
import com.gustav.restaurant_app_ea.model.dto.UserDto
import com.gustav.restaurant_app_ea.repository.UserRepository
import com.gustav.restaurant_app_ea.service.UserService
import com.gustav.restaurant_app_ea.toEntity
import org.springframework.stereotype.Service

@Service
class UserServiceImpl(
    private val userRepository: UserRepository) : UserService
{
    override fun create(user: UserDto): UserEntity {
       val userEntity = user.toEntity()

        return userRepository.save(userEntity)
    }

    override fun list(): List<UserEntity> {
        return userRepository.findAll()
    }

}
