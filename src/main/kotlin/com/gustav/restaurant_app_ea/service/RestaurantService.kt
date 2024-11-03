package com.gustav.restaurant_app_ea.service

import com.gustav.restaurant_app_ea.model.RestaurantEntity
import org.bson.types.ObjectId
import org.springframework.stereotype.Service

@Service
interface RestaurantService {
    fun save(restaurant : RestaurantEntity) : RestaurantEntity
    fun list() : List<RestaurantEntity>
    fun getById(id : ObjectId) : RestaurantEntity?
    fun update(restaurant : RestaurantEntity) : RestaurantEntity

}