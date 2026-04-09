package com.Url.UrlSorter.controller;

import com.Url.UrlSorter.dto.UrlRequest;
import com.Url.UrlSorter.entity.UrlMapping;
import com.Url.UrlSorter.service.UrlService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class UrlController {

    @Autowired
    private UrlService urlService;

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("urlRequest", new UrlRequest());
        return "index";
    }

    @PostMapping("/shorten")
    public String shortenUrl(@Valid @ModelAttribute UrlRequest urlRequest, Model model) {
        UrlMapping urlMapping = urlService.shortenUrl(urlRequest.getOriginalUrl());

        String shortUrl = "http://localhost:8080/" + urlMapping.getShortCode();

        model.addAttribute("shortUrl", shortUrl);
        model.addAttribute("originalUrl", urlRequest.getOriginalUrl());
        model.addAttribute("shortCode", urlMapping.getShortCode());

        return "result";
    }

    @GetMapping("/{shortCode}")
    public String redirectToOriginalUrl(@PathVariable String shortCode) {
        String originalUrl = urlService.getOriginalUrl(shortCode);

        if (originalUrl == null) {
            return "redirect:/"; // or error page
        }

        return "redirect:" + originalUrl;
    }
}