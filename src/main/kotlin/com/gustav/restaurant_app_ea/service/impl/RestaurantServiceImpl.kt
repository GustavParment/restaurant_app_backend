package com.gustav.restaurant_app_ea.service.impl

import com.gustav.restaurant_app_ea.model.RestaurantEntity
import com.gustav.restaurant_app_ea.model.dto.RestaurantDto
import com.gustav.restaurant_app_ea.repository.RestaurantRepository
import com.gustav.restaurant_app_ea.service.RestaurantService
import com.gustav.restaurant_app_ea.toEntity
import org.bson.types.ObjectId
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class RestaurantServiceImpl (
    private val restaurantRepository : RestaurantRepository
) : RestaurantService
{
    override fun save(restaurant: RestaurantDto): RestaurantEntity {
        val restaurantEntity = restaurant.toEntity()

        return restaurantRepository.save(restaurantEntity)
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

    override fun deleteById(id: ObjectId): Boolean {
        return if (restaurantRepository.existsById(id)){
            restaurantRepository.deleteById(id)

            true

        }else {
            println("No restaurant found for id $id")

            false
        }
    }
}