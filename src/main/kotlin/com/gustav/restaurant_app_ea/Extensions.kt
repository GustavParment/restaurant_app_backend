package com.gustav.restaurant_app_ea

import com.gustav.restaurant_app_ea.model.restaurant.RestaurantEntity
import com.gustav.restaurant_app_ea.model.user.UserEntity
import com.gustav.restaurant_app_ea.model.dto.restaurant.RestaurantDto
import com.gustav.restaurant_app_ea.model.dto.user.UserDto
import org.springframework.security.crypto.password.PasswordEncoder
import com.gustav.restaurant_app_ea.authorities.*;
import com.gustav.restaurant_app_ea.model.restaurant.ReservationEntity
import com.gustav.restaurant_app_ea.model.dto.restaurant.ReservationDto
import com.gustav.restaurant_app_ea.model.dto.restaurant.ReviewDto
import com.gustav.restaurant_app_ea.model.restaurant.Review
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
    username = this.username,
    password =this.password,
    email = this.email,
    firstName = this.firstName,
    lastName = this.lastName,
    birthday = this.birthday,
    profile = emptyList()

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
