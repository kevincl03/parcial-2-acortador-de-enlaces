package com.taller.springboot_hilos.infrastructure.adapters.out.persistence.mysql;

import com.taller.springboot_hilos.domain.model.Link;
import com.taller.springboot_hilos.domain.ports.out.LinkRepositoryPort;
import com.taller.springboot_hilos.infrastructure.adapters.out.persistence.mysql.entity.EnlaceEntity;
import com.taller.springboot_hilos.infrastructure.adapters.out.persistence.mysql.repository.SpringDataMysqlLinkRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class MysqlLinkRepositoryAdapter implements LinkRepositoryPort {

    private final SpringDataMysqlLinkRepository repository;

    public MysqlLinkRepositoryAdapter(SpringDataMysqlLinkRepository repository) {
        this.repository = repository;
    }

    @Override
    public Link save(Link link) {
        EnlaceEntity entity = new EnlaceEntity(link.getOriginalUrl(), link.getShortenedUrl());
        if (link.getId() != null) {
            entity.setIdenlace(link.getId());
        }
        EnlaceEntity saved = repository.save(entity);
        link.setId(saved.getIdenlace());
        return link;
    }

    @Override
    public Optional<Link> findByOriginalUrl(String originalUrl) {
        return repository.findByEnlaceOriginal(originalUrl).map(this::toDomain);
    }

    @Override
    public Optional<Link> findByShortenedUrl(String shortenedUrl) {
        return repository.findByEnlaceAcortado(shortenedUrl).map(this::toDomain);
    }

    @Override
    public List<Link> findAll() {
        return repository.findAll().stream().map(this::toDomain).collect(Collectors.toList());
    }

    private Link toDomain(EnlaceEntity entity) {
        Link link = new Link();
        link.setId(entity.getIdenlace());
        link.setOriginalUrl(entity.getEnlaceOriginal());
        link.setShortenedUrl(entity.getEnlaceAcortado());
        return link;
    }
}