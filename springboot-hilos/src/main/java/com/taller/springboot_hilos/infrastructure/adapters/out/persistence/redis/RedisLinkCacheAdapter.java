package com.taller.springboot_hilos.infrastructure.adapters.out.persistence.redis;

import com.taller.springboot_hilos.domain.ports.out.LinkCachePort;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Component
public class RedisLinkCacheAdapter implements LinkCachePort {

    private final StringRedisTemplate redisTemplate;
    private static final String SHORT_TO_ORIGINAL_PREFIX = "short:";
    private static final String ORIGINAL_TO_SHORT_PREFIX = "orig:";

    public RedisLinkCacheAdapter(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void cacheLink(String originalUrl, String shortenedUrl) {
        // Cache for faster lookup
        redisTemplate.opsForValue().set(SHORT_TO_ORIGINAL_PREFIX + shortenedUrl, originalUrl, 24, TimeUnit.HOURS);
        redisTemplate.opsForValue().set(ORIGINAL_TO_SHORT_PREFIX + originalUrl, shortenedUrl, 24, TimeUnit.HOURS);
    }

    @Override
    public Optional<String> getOriginalUrlByShortenedUrl(String shortenedUrl) {
        return Optional.ofNullable(redisTemplate.opsForValue().get(SHORT_TO_ORIGINAL_PREFIX + shortenedUrl));
    }

    @Override
    public Optional<String> getShortenedUrlByOriginalUrl(String originalUrl) {
        return Optional.ofNullable(redisTemplate.opsForValue().get(ORIGINAL_TO_SHORT_PREFIX + originalUrl));
    }
}