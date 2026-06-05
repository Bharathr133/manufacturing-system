package com.bharath.productionservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ProductionResponse {
    private Long orderId;
    private String message;
    private String partNumber;
    private Integer quantity;
    private String status;
}