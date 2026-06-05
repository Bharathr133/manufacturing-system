package com.bharath.qualityservice.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QualityRequest {
    private Long productionOrderId;
    private Long machineId;
    private String partNumber;
    private Integer quantityProduced;
    private Integer quantityPassed;
    private String defectType;
    private String severity;
    private String inspector;
    private String comments;
}