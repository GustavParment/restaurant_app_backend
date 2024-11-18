package com.gustav.restaurant_app_ea.repository.resturant

import com.gustav.restaurant_app_ea.model.restaurant.RestaurantEntity
import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface RestaurantRepository : MongoRepository<RestaurantEntity,ObjectId>{
}