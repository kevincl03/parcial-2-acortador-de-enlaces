package com.taller.springboot_hilos.infrastructure.adapters.in.web;

import com.taller.springboot_hilos.domain.ports.in.LinkUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
public class RedirectController {

    private final LinkUseCase linkUseCase;

    public RedirectController(LinkUseCase linkUseCase) {
        this.linkUseCase = linkUseCase;
    }

    @GetMapping("/{code}")
    public ResponseEntity<Void> redirect(@PathVariable String code) {
        try {
            String originalUrl = linkUseCase.redirect(code);
            return ResponseEntity.status(HttpStatus.FOUND)
                    .location(URI.create(originalUrl))
                    .build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
}