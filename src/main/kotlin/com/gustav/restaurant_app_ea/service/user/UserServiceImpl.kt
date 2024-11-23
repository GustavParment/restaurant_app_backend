package com.gustav.restaurant_app_ea.service.user

import com.gustav.restaurant_app_ea.authorities.Role
import com.gustav.restaurant_app_ea.config.exceptionhandling.UserNotFoundException
import com.gustav.restaurant_app_ea.model.user.UserEntity
import com.gustav.restaurant_app_ea.model.dto.user.UserDto
import com.gustav.restaurant_app_ea.model.dto.user.UserFavoriteFoodInputDto
import com.gustav.restaurant_app_ea.model.dto.user.UserHobbyInputDto
import com.gustav.restaurant_app_ea.repository.user.UserRepository
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
        return userRepository.findById(id).orElseThrow{
            UserNotFoundException("User not found with id: $id")
        }
    }

    override fun createAdmin(user: UserDto): UserEntity {
        val adminEntity = user.toAdminEntity(passwordEncoder);
        return userRepository.save(adminEntity)
    }

    override fun updateHobbies(userId: ObjectId, userHobbyInputDto: UserHobbyInputDto) {
        val userEntity = userRepository.findById(userId)
            .orElseThrow{
                UserNotFoundException("User not found")
            }
        userEntity.profile?.forEach{ profile ->
            profile.hobbies.clear()
            profile.hobbies = userHobbyInputDto.hobbies.toMutableList()
        }
        userRepository.save(userEntity)
    }

    override fun updateFavoriteFood(userId: ObjectId, userFavoriteFoodInputDto: UserFavoriteFoodInputDto) {
        val userEntity = userRepository.findById(userId)
            .orElseThrow{
                UserNotFoundException("User not found")
            }
        userEntity.profile?.forEach{ profile ->
            profile.favoriteFood.clear()
            profile.favoriteFood = userFavoriteFoodInputDto.favoriteFood.toMutableList()
        }
    }

    fun createSuperAdmin(): UserEntity {
        val existingSuperAdmin =
            userRepository.findByUsername("Super Admin") ?:
            return userRepository.save(newSuperAdmin())

        return existingSuperAdmin
    }

    private fun newSuperAdmin(): UserEntity {
        val superAdmin = UserEntity(
            username = "Super Admin",
            password = passwordEncoder.encode("123"),
            email = "superadmin@gmail.com",
            firstName = "Super",
            lastName = "Admin",
            birthday = "1337",
            role = Role.SUPER_ADMIN

        )
        return superAdmin
    }
}
