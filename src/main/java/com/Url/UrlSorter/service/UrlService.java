package com.Url.UrlSorter.service;

import com.Url.UrlSorter.entity.UrlMapping;
import com.Url.UrlSorter.repository.UrlRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class UrlService {

    @Autowired
    private UrlRepository urlRepository;

    private static final String CHARACTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final int CODE_LENGTH = 8;

    public UrlMapping shortenUrl(String originalUrl) {
        // Check if URL already exists
        return urlRepository.findAll().stream()
                .filter(url -> url.getOriginalUrl().equals(originalUrl))
                .findFirst()
                .orElseGet(() -> createNewShortUrl(originalUrl));
    }

    private UrlMapping createNewShortUrl(String originalUrl) {
        String shortCode;
        do {
            shortCode = generateShortCode();
        } while (urlRepository.existsByShortCode(shortCode));

        UrlMapping urlMapping = new UrlMapping();
        urlMapping.setOriginalUrl(originalUrl);
        urlMapping.setShortCode(shortCode);

        return urlRepository.save(urlMapping);
    }

    private String generateShortCode() {
        Random random = new Random();
        StringBuilder sb = new StringBuilder(CODE_LENGTH);
        for (int i = 0; i < CODE_LENGTH; i++) {
            sb.append(CHARACTERS.charAt(random.nextInt(CHARACTERS.length())));
        }
        return sb.toString();
    }

    public String getOriginalUrl(String shortCode) {
        return urlRepository.findByShortCode(shortCode)
                .map(url -> {
                    url.setClickCount(url.getClickCount() + 1);
                    urlRepository.save(url);
                    return url.getOriginalUrl();
                })
                .orElse(null);
    }

    public UrlMapping getUrlMapping(String shortCode) {
        return urlRepository.findByShortCode(shortCode).orElse(null);
    }
}