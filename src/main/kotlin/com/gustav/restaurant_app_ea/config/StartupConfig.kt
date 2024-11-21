package com.gustav.restaurant_app_ea.config

import com.gustav.restaurant_app_ea.service.user.UserServiceImpl
import jakarta.annotation.PostConstruct
import org.springframework.stereotype.Component

@Component
class StartupConfig(
    private val userService: UserServiceImpl
) {
    @PostConstruct
    fun initSuperAdmin() {
        println("Super Admin initialized: ${userService.createSuperAdmin().username}")
    }

}