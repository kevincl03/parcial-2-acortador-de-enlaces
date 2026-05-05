package com.taller.springboot_hilos.infrastructure.adapters.out.persistence.mongodb;

import com.taller.springboot_hilos.domain.model.Link;
import com.taller.springboot_hilos.domain.ports.out.LinkMetadataRepositoryPort;
import com.taller.springboot_hilos.infrastructure.adapters.out.persistence.mongodb.entity.EnlaceDocument;
import com.taller.springboot_hilos.infrastructure.adapters.out.persistence.mongodb.repository.SpringDataMongoLinkRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class MongoLinkRepositoryAdapter implements LinkMetadataRepositoryPort {

    private final SpringDataMongoLinkRepository repository;

    public MongoLinkRepositoryAdapter(SpringDataMongoLinkRepository repository) {
        this.repository = repository;
    }

    @Override
    public void saveMetadata(Link link) {
        Optional<EnlaceDocument> existing = repository.findByOriginalUrl(link.getOriginalUrl());
        if (existing.isEmpty()) {
            EnlaceDocument doc = new EnlaceDocument(link.getOriginalUrl(), link.getImageUrl(), link.getDescription());
            repository.save(doc);
        }
    }

    @Override
    public Optional<Link> getMetadataByOriginalUrl(String originalUrl) {
        return repository.findByOriginalUrl(originalUrl).map(doc -> {
            Link link = new Link();
            link.setOriginalUrl(doc.getOriginalUrl());
            link.setImageUrl(doc.getUrlImagen());
            link.setDescription(doc.getDescripcionUrl());
            return link;
        });
    }

    @Override
    public void populateMetadata(List<Link> links) {
        if(links.isEmpty()) return;
        List<String> urls = links.stream().map(Link::getOriginalUrl).collect(Collectors.toList());
        repository.findAllById(urls); // Triggered for init
        // simpler given constraints:
        for(Link link : links) {
            repository.findByOriginalUrl(link.getOriginalUrl()).ifPresent(doc -> {
                link.setImageUrl(doc.getUrlImagen());
                link.setDescription(doc.getDescripcionUrl());
            });
        }
    }
}