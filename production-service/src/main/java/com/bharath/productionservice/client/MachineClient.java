package com.bharath.productionservice.client;

import com.bharath.productionservice.dto.MachineDto;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;

@Service
public class MachineClient {

    private final WebClient webClient;

    public MachineClient(WebClient.Builder builder) {
        this.webClient = builder
                .baseUrl("http://MACHINE-SERVICE")
                .build();
    }

    @CircuitBreaker(name = "machineService", fallbackMethod = "getMachineFallback")
    public MachineDto getMachine(Long id) {
        System.out.println("Calling Machine Service for ID: " + id);

        return webClient
                .get()
                .uri("/machines/" + id)
                .retrieve()
                .bodyToMono(MachineDto.class)
                .block();
    }

    @CircuitBreaker(name = "machineService", fallbackMethod = "updateMachineStatusFallback")
    public MachineDto updateMachineStatus(Long id, String status) {
        System.out.println("Updating Machine " + id + " status to: " + status);

        return webClient
                .put()
                .uri("/machines/" + id + "/status")
                .bodyValue(Map.of("status", status))
                .retrieve()
                .bodyToMono(MachineDto.class)
                .block();
    }

    private MachineDto getMachineFallback(Long id, Throwable t) {
        System.out.println("Circuit breaker active. Machine Service is down. Using fallback for ID: " + id);
        System.out.println("Reason: " + t.getMessage());

        MachineDto fallback = new MachineDto();
        fallback.setId(id);
        fallback.setMachineName("Machine-" + id);
        fallback.setMachineType("UNKNOWN");
        fallback.setStatus("IDLE");
        return fallback;
    }

    private MachineDto updateMachineStatusFallback(Long id, String status, Throwable t) {
        System.out.println("Circuit breaker active. Could not update Machine " + id + " to status: " + status);
        System.out.println("Reason: " + t.getMessage());

        MachineDto fallback = new MachineDto();
        fallback.setId(id);
        fallback.setStatus(status);
        return fallback;
    }
}
