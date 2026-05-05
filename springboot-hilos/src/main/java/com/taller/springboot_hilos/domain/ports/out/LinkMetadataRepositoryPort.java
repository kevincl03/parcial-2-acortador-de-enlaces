package com.taller.springboot_hilos.domain.ports.out;

import com.taller.springboot_hilos.domain.model.Link;
import java.util.List;
import java.util.Optional;

public interface LinkMetadataRepositoryPort {
    void saveMetadata(Link link);
    Optional<Link> getMetadataByOriginalUrl(String originalUrl);
    void populateMetadata(List<Link> links);
}