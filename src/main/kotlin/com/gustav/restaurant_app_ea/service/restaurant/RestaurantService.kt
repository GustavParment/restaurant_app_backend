package com.gustav.restaurant_app_ea.service.restaurant

import com.gustav.restaurant_app_ea.model.restaurant.RestaurantEntity
import com.gustav.restaurant_app_ea.model.dto.restaurant.RestaurantDto
import org.bson.types.ObjectId


interface RestaurantService {
    fun save(restaurant : RestaurantDto) : RestaurantEntity
    fun list() : List<RestaurantEntity>
    fun getById(id : ObjectId) : RestaurantEntity?
    fun update(restaurant : RestaurantEntity) : RestaurantEntity
    fun deleteById( id : ObjectId) : Boolean


}