package com.taller.springboot_hilos.infrastructure.adapters.in.web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class ShortenLinkRequest {

    @NotBlank(message = "El enlace original es obligatorio")
    @JsonProperty("enlace_original")
    private String enlaceOriginal;

    @NotBlank(message = "La URL de la imagen es obligatoria")
    @JsonProperty("url_imagen")
    private String urlImagen;

    @NotBlank(message = "La descripción es obligatoria")
    @Size(max = 500, message = "La descripción no puede exceder los 500 caracteres")
    @JsonProperty("descripcion")
    private String descripcion;

    // Getters and setters
    public String getEnlaceOriginal() { return enlaceOriginal; }
    public void setEnlaceOriginal(String enlaceOriginal) { this.enlaceOriginal = enlaceOriginal; }
    public String getUrlImagen() { return urlImagen; }
    public void setUrlImagen(String urlImagen) { this.urlImagen = urlImagen; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
}