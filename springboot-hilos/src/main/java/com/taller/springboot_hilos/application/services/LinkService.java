package com.taller.springboot_hilos.application.services;

import com.taller.springboot_hilos.domain.model.Link;
import com.taller.springboot_hilos.domain.ports.in.LinkUseCase;
import com.taller.springboot_hilos.domain.ports.out.LinkCachePort;
import com.taller.springboot_hilos.domain.ports.out.LinkMetadataRepositoryPort;
import com.taller.springboot_hilos.domain.ports.out.LinkRepositoryPort;
import com.taller.springboot_hilos.domain.ports.out.UrlValidatorPort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Service
public class LinkService implements LinkUseCase {

    private final LinkRepositoryPort linkRepositoryPort;
    private final LinkMetadataRepositoryPort linkMetadataRepositoryPort;
    private final LinkCachePort linkCachePort;
    private final UrlValidatorPort urlValidatorPort;

    private final ConcurrentHashMap<String, Lock> urlLocks = new ConcurrentHashMap<>();

    public LinkService(LinkRepositoryPort linkRepositoryPort,
                       LinkMetadataRepositoryPort linkMetadataRepositoryPort,
                       LinkCachePort linkCachePort,
                       UrlValidatorPort urlValidatorPort) {
        this.linkRepositoryPort = linkRepositoryPort;
        this.linkMetadataRepositoryPort = linkMetadataRepositoryPort;
        this.linkCachePort = linkCachePort;
        this.urlValidatorPort = urlValidatorPort;
    }

    @Override
    public Link createShortLink(String originalUrl, String imageUrl, String description) {
        validateInputs(originalUrl, imageUrl, description);

        Lock lock = urlLocks.computeIfAbsent(originalUrl, k -> new ReentrantLock());
        lock.lock();
        try {
            // Check cache first
            Optional<String> cachedShortUrl = linkCachePort.getShortenedUrlByOriginalUrl(originalUrl);
            if (cachedShortUrl.isPresent()) {
                Optional<Link> cachedLink = getLinkByShortCode(cachedShortUrl.get());
                if (cachedLink.isPresent()) {
                    return cachedLink.get();
                }
            }

            // Check DB
            Optional<Link> existingLink = linkRepositoryPort.findByOriginalUrl(originalUrl);
            if (existingLink.isPresent()) {
                Link link = existingLink.get();
                linkMetadataRepositoryPort.getMetadataByOriginalUrl(originalUrl).ifPresent(metadata -> {
                    link.setImageUrl(metadata.getImageUrl());
                    link.setDescription(metadata.getDescription());
                });
                cacheIfRequired(link);
                return link;
            }

            String shortCode = generateShortCode();

            Link newLink = new Link(originalUrl, shortCode, imageUrl, description);
            
            // Save to MySQL
            Link savedLink = linkRepositoryPort.save(newLink);
            
            // Save to MongoDB
            linkMetadataRepositoryPort.saveMetadata(savedLink);

            // Cache in Redis
            cacheIfRequired(savedLink);

            return savedLink;
        } finally {
            lock.unlock();
            urlLocks.remove(originalUrl);
        }
    }

    @Override
    public Optional<Link> getLinkByShortCode(String shortCode) {
        Optional<Link> linkOpt = linkRepositoryPort.findByShortenedUrl(shortCode);
        linkOpt.ifPresent(link -> {
            linkMetadataRepositoryPort.getMetadataByOriginalUrl(link.getOriginalUrl()).ifPresent(metadata -> {
                link.setImageUrl(metadata.getImageUrl());
                link.setDescription(metadata.getDescription());
            });
        });
        return linkOpt;
    }

    @Override
    public List<Link> getAllLinks() {
        List<Link> links = linkRepositoryPort.findAll();
        linkMetadataRepositoryPort.populateMetadata(links);
        return links;
    }

    @Override
    public boolean existsOriginalUrl(String originalUrl) {
        return linkCachePort.getShortenedUrlByOriginalUrl(originalUrl).isPresent() 
                || linkRepositoryPort.findByOriginalUrl(originalUrl).isPresent();
    }

    @Override
    public String redirect(String shortCode) {
        // Try cache first
        Optional<String> originalUrlOpt = linkCachePort.getOriginalUrlByShortenedUrl(shortCode);
        if (originalUrlOpt.isPresent()) {
            return originalUrlOpt.get();
        }

        return linkRepositoryPort.findByShortenedUrl(shortCode)
                .map(Link::getOriginalUrl)
                .orElseThrow(() -> new IllegalArgumentException("Short code not found: " + shortCode));
    }

    private void validateInputs(String originalUrl, String imageUrl, String description) {
        if (originalUrl == null || originalUrl.trim().isEmpty() || !urlValidatorPort.isValid(originalUrl)) {
            throw new IllegalArgumentException("Invalid original URL");
        }
        if (imageUrl == null || imageUrl.trim().isEmpty() || !urlValidatorPort.isValid(imageUrl)) {
            throw new IllegalArgumentException("Invalid image URL");
        }
        if (description == null || description.trim().isEmpty()) {
            throw new IllegalArgumentException("Description is required");
        }
        if (description.length() > 500) {
            throw new IllegalArgumentException("Description exceeds 500 characters");
        }
        if (description.trim().split("\\s+").length < 5) {
            throw new IllegalArgumentException("Description must contain at least 5 words");
        }
    }

    private String generateShortCode() {
        return UUID.randomUUID().toString().substring(0, 8).replace("-", "");
    }

    private void cacheIfRequired(Link link) {
        if (link.getOriginalUrl().length() > 50) {
            linkCachePort.cacheLink(link.getOriginalUrl(), link.getShortenedUrl());
        }
    }
}