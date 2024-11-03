package com.gustav.restaurant_app_ea.config

import io.github.resilience4j.ratelimiter.RateLimiter
import io.github.resilience4j.ratelimiter.RateLimiterConfig
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.time.Duration

@Configuration
class RateLimiterConfigClass {

    @Bean
    fun rateLimiterConfig(): RateLimiter {
        val config = RateLimiterConfig.custom()
            .limitForPeriod(10)
            .limitRefreshPeriod(Duration.ofMinutes(1))
            .timeoutDuration(Duration.ofSeconds(1))
            .build()
        return RateLimiter.of("rateLimiter", config)
    }

}