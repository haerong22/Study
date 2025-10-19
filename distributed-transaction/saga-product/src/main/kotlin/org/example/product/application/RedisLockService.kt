package org.example.product.application

import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.stereotype.Service

@Service
class RedisLockService(
    private val stringRedisTemplate: StringRedisTemplate,
) {

    fun tryLock(key: String, value: String): Boolean {
        return stringRedisTemplate.opsForValue().setIfAbsent(key, value) ?: false
    }

    fun releaseLock(key: String) {
        stringRedisTemplate.delete(key)
    }
}