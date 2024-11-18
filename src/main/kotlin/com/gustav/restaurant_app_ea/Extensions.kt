package com.gustav.restaurant_app_ea

import com.gustav.restaurant_app_ea.model.RestaurantEntity
import com.gustav.restaurant_app_ea.model.UserEntity
import com.gustav.restaurant_app_ea.model.dto.RestaurantDto
import com.gustav.restaurant_app_ea.model.dto.UserDto
import org.springframework.security.crypto.password.PasswordEncoder
import com.gustav.restaurant_app_ea.authorities.*;

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
)

fun UserEntity.toDto(): UserDto = UserDto(
    username = this.username,
    password =this.password,
    email = this.email,
    firstName = this.firstName,
    lastName = this.lastName,
    birthday = this.birthday,

)

fun UserDto.toEntity(passwordEncoder: PasswordEncoder): UserEntity = UserEntity(
    username = this.username,
    password = passwordEncoder.encode(this.password),
    email = this.email,
    firstName = this.firstName,
    lastName = this.lastName,
    birthday = this.birthday,
    role = Role.USER


)

fun UserDto.toAdminEntity(passwordEncoder: PasswordEncoder): UserEntity = UserEntity(
    username = this.username,
    password = passwordEncoder.encode(this.password),
    email = this.email,
    firstName = this.firstName,
    lastName = this.lastName,
    birthday = this.birthday,
    role = Role.ADMIN
)