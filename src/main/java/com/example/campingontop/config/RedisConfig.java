package com.example.campingontop.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
@RequiredArgsConstructor
public class RedisConfig {
    private final RedisProperty redisProperty;

    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        return new LettuceConnectionFactory(redisProperty.getRedisHost(), redisProperty.getRedisPort());
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate() {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new StringRedisSerializer());
        redisTemplate.setConnectionFactory(redisConnectionFactory());
        return redisTemplate;
    }
}

/*
        <key 확인>
        127.0.0.1:6379> keys *
        1) "FREE_CAMPING"
        2) "FREE_CAMPING_COUNT"

        <쿠폰 수량 확인>
        127.0.0.1:6379> GET FREE_CAMPING_COUNT
        "3"

        <집합 내의 모든 원소 + timestamp 함께 반환>
        127.0.0.1:6379> ZRANGE FREE_CAMPING 0 -1 WITHSCORES
        1) "test01@naver.com"
        2) "1717383776563"
*/
