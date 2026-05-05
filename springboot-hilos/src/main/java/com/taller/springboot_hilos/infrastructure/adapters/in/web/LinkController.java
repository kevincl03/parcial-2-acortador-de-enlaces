package com.taller.springboot_hilos.infrastructure.adapters.in.web;

import com.taller.springboot_hilos.domain.model.Link;
import com.taller.springboot_hilos.domain.ports.in.LinkUseCase;
import com.taller.springboot_hilos.infrastructure.adapters.in.web.dto.LinkResponse;
import com.taller.springboot_hilos.infrastructure.adapters.in.web.dto.ShortenLinkRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/links")
@CrossOrigin(origins = "*")
public class LinkController {

    private final LinkUseCase linkUseCase;

    public LinkController(LinkUseCase linkUseCase) {
        this.linkUseCase = linkUseCase;
    }

    @PostMapping("/shorten")
    public CompletableFuture<ResponseEntity<LinkResponse>> shortenLink(@Valid @RequestBody ShortenLinkRequest request) {
        return CompletableFuture.supplyAsync(() -> {
            Link link = linkUseCase.createShortLink(
                request.getEnlaceOriginal(),
                request.getUrlImagen(),
                request.getDescripcion()
            );
            return ResponseEntity.status(HttpStatus.CREATED).body(new LinkResponse(link));
        });
    }

    @GetMapping
    public CompletableFuture<ResponseEntity<List<LinkResponse>>> getAllLinks() {
        return CompletableFuture.supplyAsync(() -> {
            List<LinkResponse> responses = linkUseCase.getAllLinks().stream()
                    .map(LinkResponse::new)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(responses);
        });
    }

    @GetMapping("/{code}")
    public CompletableFuture<ResponseEntity<LinkResponse>> getLinkByCode(@PathVariable String code) {
        return CompletableFuture.supplyAsync(() -> linkUseCase.getLinkByShortCode(code)
                .map(link -> ResponseEntity.ok(new LinkResponse(link)))
                .orElseGet(() -> ResponseEntity.notFound().build()));
    }

    @GetMapping("/exists")
    public CompletableFuture<ResponseEntity<Boolean>> checkLinkExists(@RequestParam("url") String originalUrl) {
        return CompletableFuture.supplyAsync(() -> {
            boolean exists = linkUseCase.existsOriginalUrl(originalUrl);
            return ResponseEntity.ok(exists);
        });
    }
}