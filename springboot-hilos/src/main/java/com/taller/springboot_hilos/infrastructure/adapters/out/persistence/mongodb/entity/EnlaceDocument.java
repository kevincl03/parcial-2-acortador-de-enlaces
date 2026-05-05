package com.taller.springboot_hilos.infrastructure.adapters.out.persistence.mongodb.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "enlace")
public class EnlaceDocument {
    @Id
    private String id;
    
    @Field("original_url")
    private String originalUrl;
    
    @Field("url_imagen")
    private String urlImagen;
    
    @Field("descripcion_url")
    private String descripcionUrl;

    public EnlaceDocument() {}

    public EnlaceDocument(String originalUrl, String urlImagen, String descripcionUrl) {
        this.originalUrl = originalUrl;
        this.urlImagen = urlImagen;
        this.descripcionUrl = descripcionUrl;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getOriginalUrl() { return originalUrl; }
    public void setOriginalUrl(String originalUrl) { this.originalUrl = originalUrl; }
    public String getUrlImagen() { return urlImagen; }
    public void setUrlImagen(String urlImagen) { this.urlImagen = urlImagen; }
    public String getDescripcionUrl() { return descripcionUrl; }
    public void setDescripcionUrl(String descripcionUrl) { this.descripcionUrl = descripcionUrl; }
}