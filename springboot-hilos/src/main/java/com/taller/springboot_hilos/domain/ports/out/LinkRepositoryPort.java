package com.taller.springboot_hilos.domain.ports.out;

import com.taller.springboot_hilos.domain.model.Link;
import java.util.List;
import java.util.Optional;

public interface LinkRepositoryPort {
    Link save(Link link);
    Optional<Link> findByOriginalUrl(String originalUrl);
    Optional<Link> findByShortenedUrl(String shortenedUrl);
    List<Link> findAll();
}