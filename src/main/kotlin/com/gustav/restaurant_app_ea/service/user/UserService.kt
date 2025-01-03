package com.gustav.restaurant_app_ea.service.user

import com.gustav.restaurant_app_ea.model.dto.user.LikeRequest
import com.gustav.restaurant_app_ea.model.user.UserEntity
import com.gustav.restaurant_app_ea.model.dto.user.UserDto
import com.gustav.restaurant_app_ea.model.dto.user.UserFavoriteFoodInputDto
import com.gustav.restaurant_app_ea.model.dto.user.UserHobbyInputDto
import org.bson.types.ObjectId


interface UserService {
    fun create(user: UserDto): UserEntity
    fun list(): List<UserDto>
    fun findByUsername(username: String): UserEntity?
    fun findById(id: String): UserEntity?
    fun createAdmin(user: UserDto): UserEntity
    fun updateHobbies(userId: String, userHobbyInputDto: UserHobbyInputDto)
    fun updateFavoriteFood(userId: String, userFavoriteFoodInputDto: UserFavoriteFoodInputDto)
    fun updateUser(id:String,userDto: UserDto) : UserEntity
    fun updateAvatar(userId: String, userAvatarUrl: String)
    fun updateBio(userId: String, userBio: String)
    fun deleteUser(user: UserEntity)
    fun updateLikes(userId: String, like: Int)
}