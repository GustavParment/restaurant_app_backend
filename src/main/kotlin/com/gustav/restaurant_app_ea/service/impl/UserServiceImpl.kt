package com.gustav.restaurant_app_ea.service.impl

import com.gustav.restaurant_app_ea.model.user.UserEntity
import com.gustav.restaurant_app_ea.model.dto.user.UserDto
import com.gustav.restaurant_app_ea.repository.user.UserRepository
import com.gustav.restaurant_app_ea.service.user.UserService
import com.gustav.restaurant_app_ea.toAdminEntity
import com.gustav.restaurant_app_ea.toEntity
import org.bson.types.ObjectId
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserServiceImpl(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder
) : UserService

{
    override fun create(user: UserDto): UserEntity {
       val userEntity = user.toEntity(passwordEncoder)

        return userRepository.save(userEntity)
    }

    override fun list(): List<UserEntity> {
        return userRepository.findAll()
    }

    override fun findByUsername(username: String): UserEntity? {
        return userRepository.findByUsername(username)
    }

    override fun findById(id: ObjectId): UserEntity? {
        return userRepository.findById(id).orElseThrow()
    }

    override fun createAdmin(user: UserDto): UserEntity {
        val adminEntity = user.toAdminEntity(passwordEncoder);
        return userRepository.save(adminEntity)
    }

}
