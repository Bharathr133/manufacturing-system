package com.bharath.apigateway;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ApiGatewayApplication {

    @Value("${spring.application.name:NOT_FOUND}")
    private String appName;

    @PostConstruct
    public void test() {
        System.out.println("APP NAME = " + appName);
    }

    public static void main(String[] args) {
        SpringApplication.run(ApiGatewayApplication.class, args);
    }
}