package com.example.trainbooking.config;

import java.time.Duration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.github.resilience4j.ratelimiter.RateLimiterConfig;
import io.github.resilience4j.ratelimiter.RateLimiterRegistry;

@Configuration
public class RateLimiterConfiguration {

    @Bean
    public RateLimiterRegistry rateLimiterRegistry() {
        RateLimiterConfig config = RateLimiterConfig.custom()
                                                    .timeoutDuration(Duration.ofSeconds(1))
                                                    .limitRefreshPeriod(Duration.ofSeconds(1))
                                                    .limitForPeriod(3) // allow 3 requests per second
                                                    .build();

        return RateLimiterRegistry.of(config);
    }
}
