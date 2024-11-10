package com.gustav.restaurant_app_ea.service

import com.gustav.restaurant_app_ea.model.RestaurantEntity
import com.gustav.restaurant_app_ea.model.dto.RestaurantDto
import org.bson.types.ObjectId


interface RestaurantService {
    fun save(restaurant : RestaurantDto) : RestaurantEntity
    fun list() : List<RestaurantEntity>
    fun getById(id : ObjectId) : RestaurantEntity?
    fun update(restaurant : RestaurantEntity) : RestaurantEntity
    fun deleteById( id : ObjectId) : Boolean

}