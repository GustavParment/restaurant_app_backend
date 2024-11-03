package com.gustav.restaurant_app_ea

import com.gustav.restaurant_app_ea.model.RestaurantEntity
import com.gustav.restaurant_app_ea.model.dto.RestaurantDto

fun RestaurantEntity.toRestaurantDto(): RestaurantDto = RestaurantDto(
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

fun RestaurantDto.toRestaurantEntity(): RestaurantEntity = RestaurantEntity(
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