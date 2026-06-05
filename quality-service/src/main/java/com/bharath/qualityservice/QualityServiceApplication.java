package com.bharath.qualityservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient  // ← ADD THIS LINE
public class QualityServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(QualityServiceApplication.class, args);
    }
}