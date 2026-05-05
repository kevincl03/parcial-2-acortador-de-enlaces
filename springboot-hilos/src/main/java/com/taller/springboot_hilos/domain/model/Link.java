package com.taller.springboot_hilos.domain.model;

public class Link {
    private Integer id;
    private String originalUrl;
    private String shortenedUrl;
    private String imageUrl;
    private String description;

    public Link() {
    }

    public Link(String originalUrl, String shortenedUrl, String imageUrl, String description) {
        this.originalUrl = originalUrl;
        this.shortenedUrl = shortenedUrl;
        this.imageUrl = imageUrl;
        this.description = description;
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getOriginalUrl() { return originalUrl; }
    public void setOriginalUrl(String originalUrl) { this.originalUrl = originalUrl; }
    public String getShortenedUrl() { return shortenedUrl; }
    public void setShortenedUrl(String shortenedUrl) { this.shortenedUrl = shortenedUrl; }
    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}