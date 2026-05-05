package com.taller.springboot_hilos.domain.ports.out;

import java.util.Optional;

public interface LinkCachePort {
    void cacheLink(String originalUrl, String shortenedUrl);
    Optional<String> getOriginalUrlByShortenedUrl(String shortenedUrl);
    Optional<String> getShortenedUrlByOriginalUrl(String originalUrl);
}