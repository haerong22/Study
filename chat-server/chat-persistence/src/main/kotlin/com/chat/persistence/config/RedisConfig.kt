package com.chat.persistence.config

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.KotlinModule
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.listener.RedisMessageListenerContainer
import org.springframework.data.redis.serializer.StringRedisSerializer
import java.util.concurrent.Executors

@Configuration
class RedisConfig {

    @Bean
    fun distributedObjectMapper(): ObjectMapper {
        return ObjectMapper().apply {
            registerModule(KotlinModule.Builder().build())
            registerModule(JavaTimeModule())
            disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
            configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
        }
    }

    @Bean
    fun redisTemplate(connectionFactory: RedisConnectionFactory): RedisTemplate<String, String> {
        return RedisTemplate<String, String>().apply {
            setConnectionFactory(connectionFactory)
            keySerializer = StringRedisSerializer()
            valueSerializer = StringRedisSerializer()
            hashKeySerializer = StringRedisSerializer()
            hashValueSerializer = StringRedisSerializer()
            afterPropertiesSet()
        }
    }

    @Bean
    fun redisMessageListenerContainer(
        connectionFactory: RedisConnectionFactory,
    ): RedisMessageListenerContainer {
        return RedisMessageListenerContainer().apply {
            setConnectionFactory(connectionFactory)
            setTaskExecutor(Executors.newCachedThreadPool { runnable ->
                Thread(runnable).apply {
                    name = "redis-message-listener-container-${System.currentTimeMillis()}"
                    isDaemon = true
                }
            })
            setErrorHandler { e ->
                println("Redis Message Listener Error: $e")
                e.printStackTrace()
            }
        }
    }
}