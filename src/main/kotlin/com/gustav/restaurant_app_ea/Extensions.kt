package com.gustav.restaurant_app_ea

import com.gustav.restaurant_app_ea.model.dto.restaurant.ReservationDto
import com.gustav.restaurant_app_ea.model.restaurant.RestaurantEntity
import com.gustav.restaurant_app_ea.model.user.UserEntity
import com.gustav.restaurant_app_ea.model.dto.restaurant.RestaurantDto
import com.gustav.restaurant_app_ea.model.dto.user.UserDto
import org.springframework.security.crypto.password.PasswordEncoder
import com.gustav.restaurant_app_ea.model.dto.restaurant.ReviewDto
import com.gustav.restaurant_app_ea.model.dto.user.ChatDto
import com.gustav.restaurant_app_ea.model.restaurant.ReservationEntity
import com.gustav.restaurant_app_ea.model.restaurant.Review
import com.gustav.restaurant_app_ea.model.user.ChatEntity
import com.gustav.restaurant_app_ea.security.authorities.Role
import java.time.LocalDateTime

fun RestaurantEntity.toDto() = RestaurantDto(
    id = this.id,
    name = this.name,
    address = this.address,
    phone = this.phone,
    email = this.email,
    description = this.description,
    image = this.image,
    rating = this.rating,
    menu = this.menu,
    review =this.review,

)

fun RestaurantDto.toEntity() = RestaurantEntity(
    id = this.id,
    name = this.name,
    address = this.address,
    phone = this.phone,
    email = this.email,
    description = this.description,
    image = this.image,
    rating = this.rating,
    menu = this.menu,
    review = this.review,
)

fun UserEntity.toDto(): UserDto = UserDto(
    id = this.id,
    username = this.username,
    password =this.password,
    email = this.email,
    firstName = this.firstName,
    lastName = this.lastName,
    birthday = this.birthday,
    profile = emptyList(),
    matchList = emptyList(),
    role = this.role

)

fun UserDto.toEntity(passwordEncoder: PasswordEncoder): UserEntity = UserEntity(
    username = this.username,
    password = passwordEncoder.encode(this.password),
    email = this.email,
    firstName = this.firstName,
    lastName = this.lastName,
    birthday = this.birthday,
    role = Role.USER,
    profile = emptyList()


)

fun UserDto.toAdminEntity(passwordEncoder: PasswordEncoder): UserEntity = UserEntity(
    username = this.username,
    password = passwordEncoder.encode(this.password),
    email = this.email,
    firstName = this.firstName,
    lastName = this.lastName,
    birthday = this.birthday,
    role = Role.ADMIN,
    profile = emptyList()
)

fun UserDto.toSuperAdminEntity(passwordEncoder: PasswordEncoder): UserEntity = UserEntity(
    username = this.username,
    password = passwordEncoder.encode(this.password),
    email = this.email,
    firstName = this.firstName,
    lastName = this.lastName,
    birthday = this.birthday,
    role = Role.SUPER_ADMIN,
    profile = emptyList()

)

fun Review.toDto(): ReviewDto = ReviewDto(
    restaurantId = this.restaurantId,
    userId = this.userId,
    comment = this.comment,
    userName = this.userName,
    rating = this.rating,
)

fun ReviewDto.toEntity(): Review = Review(
    userId = this.userId,
    restaurantId = this.restaurantId,
    comment = this.comment,
    userName = this.userName,
    rating = this.rating,
    date = LocalDateTime.now()
)

fun ChatEntity.toDto(): ChatDto = ChatDto(
    ownerId = this.ownerId,
    engagedUserId = this.engagedUserId,
    message = this.chatHistory,
)

fun ChatDto.toEntity(): ChatEntity = ChatEntity(
    ownerId = this.ownerId,
    engagedUserId = this.engagedUserId,
    timestamp = LocalDateTime.now(),
    chatHistory = this.message,
)
fun ReservationEntity.toDto(): ReservationDto = ReservationDto(
    id = this.id,
    restaurantId = this.restaurantId,
    userId = this.userId,
    reservationDate = this.reservationDate,
    guests = this.guests

)

fun ReservationDto.toEntity(): ReservationEntity = ReservationEntity(
    id = this.id,
    restaurantId = this.restaurantId,
    userId = this.userId,
    reservationDate = this.reservationDate,
    guests = this.guests,
)

fun List<UserEntity>.toUserListDto(): List<UserDto> {
    return this.map { it.toDto() }
}

fun List<RestaurantEntity>.toRestaurantListDto(): List<RestaurantDto> {
    return this.map { it.toDto() }
}