package com.task.util;

import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import reactor.core.publisher.Mono;
import org.springframework.web.reactive.function.client.WebClient;


@Component
public class WebClientConnector {

    private final WebClient webClient;
    private static final String BASE_URL = "http://localhost:8080";

    public WebClientConnector() {
        this.webClient = WebClient.builder()
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .baseUrl(BASE_URL)
                .build();
    }
    public ResponseEntity<Object> create(String url, HttpEntity<?> request)  {
        return webClient
                .post()
                .uri(url)
                .bodyValue(request.getBody())
                .retrieve()
                .toEntity(Object.class)
                .block();
    }
    public ResponseEntity<Object> getById(String url) {
        return webClient
                .get()
                .uri(url)
                .retrieve()
                .toEntity(Object.class).block();
    }
    
    public ResponseEntity<List<Object>> getAll(String url) {
        return webClient
                .get()
                .uri(url)
                .retrieve()
                .toEntityList(Object.class).block();
    }


    public ResponseEntity<Object> update(String url, HttpEntity<?> request) {
        return webClient
                .put()
                .uri(url)
                .bodyValue(request.getBody())
                .retrieve()
                .toEntity(Object.class).block();
    }

    public ResponseEntity<Object> delete(String url) {
        return webClient
                .delete()
                .uri(url)
                .retrieve()
                .toEntity(Object.class).block();
    }

}