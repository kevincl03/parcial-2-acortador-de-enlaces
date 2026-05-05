package com.taller.springboot_hilos.infrastructure.adapters.out.persistence.mongodb.repository;

import com.taller.springboot_hilos.infrastructure.adapters.out.persistence.mongodb.entity.EnlaceDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SpringDataMongoLinkRepository extends MongoRepository<EnlaceDocument, String> {
    Optional<EnlaceDocument> findByOriginalUrl(String originalUrl);
}