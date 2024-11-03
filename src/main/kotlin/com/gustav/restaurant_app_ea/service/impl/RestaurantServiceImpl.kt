package com.gustav.restaurant_app_ea.service.impl

import com.gustav.restaurant_app_ea.model.RestaurantEntity
import com.gustav.restaurant_app_ea.repository.RestaurantRepository
import com.gustav.restaurant_app_ea.service.RestaurantService
import org.bson.types.ObjectId
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class RestaurantServiceImpl (
    private val restaurantRepository : RestaurantRepository
)
    :RestaurantService
{
    override fun save(restaurant: RestaurantEntity): RestaurantEntity {
        return restaurantRepository.save(restaurant)
    }

    override fun list(): List<RestaurantEntity> {
        return restaurantRepository.findAll()
    }

    override fun getById(id: ObjectId): RestaurantEntity? {
        return restaurantRepository.findByIdOrNull(id)
    }

    override fun update(restaurant: RestaurantEntity): RestaurantEntity {
        TODO("Not yet implemented")
    }
}