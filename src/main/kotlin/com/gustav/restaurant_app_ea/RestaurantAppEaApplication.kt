package com.gustav.restaurant_app_ea

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class RestaurantAppEaApplication

fun main(args: Array<String>) {
    runApplication<RestaurantAppEaApplication>(*args)
    println("<-----App is running----->")
}
