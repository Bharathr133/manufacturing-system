package com.bharath.productionservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class QualityResponse {
    private Long id;
    private String status;
    private Integer quantityPassed;
    private Integer quantityFailed;
    private String message;
}