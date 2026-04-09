package com.Url.UrlSorter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.Url.UrlSorter.repository")
public class UrlSorterApplication {

    public static void main(String[] args) {
        SpringApplication.run(UrlSorterApplication.class, args);
    }
}