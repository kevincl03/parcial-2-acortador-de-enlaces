package com.taller.springboot_hilos.domain.ports.out;

public interface UrlValidatorPort {
    boolean isValid(String url);
}