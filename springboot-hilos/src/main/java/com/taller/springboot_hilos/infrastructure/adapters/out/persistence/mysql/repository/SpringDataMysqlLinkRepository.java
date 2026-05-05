package com.taller.springboot_hilos.infrastructure.adapters.out.persistence.mysql.repository;

import com.taller.springboot_hilos.infrastructure.adapters.out.persistence.mysql.entity.EnlaceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SpringDataMysqlLinkRepository extends JpaRepository<EnlaceEntity, Integer> {
    Optional<EnlaceEntity> findByEnlaceOriginal(String enlaceOriginal);
    Optional<EnlaceEntity> findByEnlaceAcortado(String enlaceAcortado);
}