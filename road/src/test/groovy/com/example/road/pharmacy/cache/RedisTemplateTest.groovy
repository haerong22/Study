package com.example.road.pharmacy.cache

import com.example.road.AbstractIntegrationContainerBaseTest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.redis.core.RedisTemplate

class RedisTemplateTest extends AbstractIntegrationContainerBaseTest {

    @Autowired
    private RedisTemplate redisTemplate;

    def "RedisTemplate String operations"() {
        given:
        def valueOps = redisTemplate.opsForValue()
        def key = "key"
        def value = "hello"

        when:
        valueOps.set(key, value)

        then:
        def result = valueOps.get(key)
        result == value
    }

    def "RedisTemplate set operations"() {
        given:
        def setOps = redisTemplate.opsForSet()
        def key = "setKey"

        when:
        setOps.add(key, "h", "e", "l", "l", "o")

        then:
        def size = setOps.size(key)
        size == 4
    }

    def "RedisTemplate hash operations"() {
        given:
        def hashOps = redisTemplate.opsForHash()
        def key = "hashKey"
        def subKey = "subKey"
        def value = "value"

        when:
        hashOps.put(key, subKey, value)

        then:
        def result = hashOps.get(key, subKey)
        result == value

        def entries = hashOps.entries(key)
        entries.keySet().contains(subKey)
        entries.values().contains(value)

        def size = hashOps.size(key)
        size == entries.size()
    }
}
