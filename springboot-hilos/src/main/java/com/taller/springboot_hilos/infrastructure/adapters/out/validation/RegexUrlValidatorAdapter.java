package com.taller.springboot_hilos.infrastructure.adapters.out.validation;

import com.taller.springboot_hilos.domain.ports.out.UrlValidatorPort;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public class RegexUrlValidatorAdapter implements UrlValidatorPort {

    private static final String URL_REGEX = "^(https?|ftp)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";
    private static final Pattern URL_PATTERN = Pattern.compile(URL_REGEX);

    @Override
    public boolean isValid(String url) {
        if (url == null) return false;
        return URL_PATTERN.matcher(url).matches();
    }
}