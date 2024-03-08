package com.example.performance.config;

import lombok.RequiredArgsConstructor;
import org.ehcache.config.builders.CacheEventListenerConfigurationBuilder;
import org.ehcache.event.EventType;
import org.ehcache.jsr107.Eh107Configuration;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.SimpleKey;
import org.springframework.cache.jcache.JCacheCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.cache.Caching;
import java.util.ArrayList;

import static java.time.Duration.ofSeconds;
import static org.ehcache.config.builders.CacheConfigurationBuilder.newCacheConfigurationBuilder;
import static org.ehcache.config.builders.ExpiryPolicyBuilder.timeToIdleExpiration;
import static org.ehcache.config.builders.ExpiryPolicyBuilder.timeToLiveExpiration;
import static org.ehcache.config.builders.ResourcePoolsBuilder.heap;

@Configuration
@EnableCaching
@RequiredArgsConstructor
public class EhcacheConfig {

    private final CacheEventLogger cacheEventLogger;

    @Bean
    public CacheManager cacheManager() {
        var cacheManager = Caching.getCachingProvider().getCacheManager();

        cacheManager.createCache("NoticeReadMapper.findByPage", cacheConfiguration(String.class, ArrayList.class));
        cacheManager.createCache("NoticeReadMapper.findAll", cacheConfiguration(SimpleKey.class, ArrayList.class));

        return new JCacheCacheManager(cacheManager);
    }

    private javax.cache.configuration.Configuration<?, ?> cacheConfiguration(Class<?> key, Class<?> value) {
        var config = newCacheConfigurationBuilder(key, value, heap(10000))
                .withService(listenerConfiguration())
                .withExpiry(timeToIdleExpiration(ofSeconds(10)))
                .withExpiry(timeToLiveExpiration(ofSeconds(10)));
        return Eh107Configuration.fromEhcacheCacheConfiguration(config);
    }

    private CacheEventListenerConfigurationBuilder listenerConfiguration() {
        return CacheEventListenerConfigurationBuilder
                .newEventListenerConfiguration(cacheEventLogger, EventType.CREATED, EventType.UPDATED)
                .unordered().asynchronous();
    }
}