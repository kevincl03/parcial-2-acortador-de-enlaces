package com.taller.springboot_hilos.infrastructure.adapters.in.web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.taller.springboot_hilos.domain.model.Link;

public class LinkResponse {
    @JsonProperty("enlace_original")
    private String enlaceOriginal;
    
    @JsonProperty("enlace_acortado")
    private String enlaceAcortado;
    
    @JsonProperty("url_imagen")
    private String urlImagen;
    
    @JsonProperty("descripcion")
    private String descripcion;

    // Constructors
    public LinkResponse() {}

    public LinkResponse(Link link) {
        this.enlaceOriginal = link.getOriginalUrl();
        this.enlaceAcortado = link.getShortenedUrl();
        this.urlImagen = link.getImageUrl();
        this.descripcion = link.getDescription();
    }

    public String getEnlaceOriginal() { return enlaceOriginal; }
    public String getEnlaceAcortado() { return enlaceAcortado; }
    public String getUrlImagen() { return urlImagen; }
    public String getDescripcion() { return descripcion; }
}