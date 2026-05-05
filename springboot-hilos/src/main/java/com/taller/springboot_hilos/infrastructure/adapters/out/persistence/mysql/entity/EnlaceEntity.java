package com.taller.springboot_hilos.infrastructure.adapters.out.persistence.mysql.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column;

@Entity
@Table(name = "enlace")
public class EnlaceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idenlace")
    private Integer idenlace;

    @Column(name = "enlace_original", length = 2048)
    private String enlaceOriginal;

    @Column(name = "enlace_acortado", length = 50, unique = true)
    private String enlaceAcortado;

    public EnlaceEntity() {}

    public EnlaceEntity(String enlaceOriginal, String enlaceAcortado) {
        this.enlaceOriginal = enlaceOriginal;
        this.enlaceAcortado = enlaceAcortado;
    }

    public Integer getIdenlace() { return idenlace; }
    public void setIdenlace(Integer idenlace) { this.idenlace = idenlace; }
    public String getEnlaceOriginal() { return enlaceOriginal; }
    public void setEnlaceOriginal(String enlaceOriginal) { this.enlaceOriginal = enlaceOriginal; }
    public String getEnlaceAcortado() { return enlaceAcortado; }
    public void setEnlaceAcortado(String enlaceAcortado) { this.enlaceAcortado = enlaceAcortado; }
}