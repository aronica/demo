package com.example.demo.configuration;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableCaching
@ConditionalOnProperty(prefix = "store.redis", name = "enabled", havingValue = "true")
public class RedisTemplateConfiguration {

    @org.springframework.context.annotation.Bean(name = "defaultRedisTemplate")
    public <T> org.springframework.data.redis.core.RedisTemplate<String, T> defaultRedisTemplate(
      @Qualifier("defaultRedisConnectionFactory") org.springframework.data.redis.connection.RedisConnectionFactory connectionFactory) {
        org.springframework.data.redis.core.RedisTemplate<String, T> redisTemplate = new org.springframework.data.redis.core.RedisTemplate<>();
        redisTemplate.setConnectionFactory(connectionFactory);
        return redisTemplate;
    }


}
