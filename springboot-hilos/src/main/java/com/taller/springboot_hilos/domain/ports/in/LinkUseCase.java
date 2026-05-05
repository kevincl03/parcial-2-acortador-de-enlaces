package com.taller.springboot_hilos.domain.ports.in;

import com.taller.springboot_hilos.domain.model.Link;
import java.util.List;
import java.util.Optional;

public interface LinkUseCase {
    Link createShortLink(String originalUrl, String imageUrl, String description);
    Optional<Link> getLinkByShortCode(String shortCode);
    List<Link> getAllLinks();
    boolean existsOriginalUrl(String originalUrl);
    String redirect(String shortCode);
}