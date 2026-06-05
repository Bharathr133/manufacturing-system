package com.bharath.productionservice.client;

import com.bharath.productionservice.dto.QualityRequest;
import com.bharath.productionservice.dto.QualityResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class QualityClient {

    private final WebClient webClient;

    public QualityClient(WebClient.Builder builder) {
        this.webClient = builder
                .baseUrl("http://QUALITY-SERVICE")
                .build();
    }

    public QualityResponse recordQualityCheck(QualityRequest request) {
        return webClient
                .post()
                .uri("/quality/check")
                .bodyValue(request)
                .retrieve()
                .bodyToMono(QualityResponse.class)
                .block();
    }
}