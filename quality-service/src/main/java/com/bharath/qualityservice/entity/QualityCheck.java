package com.bharath.qualityservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;

@Entity
@Table(name = "quality_check")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class QualityCheck {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long productionOrderId;
    private Long machineId;
    private String partNumber;
    private Integer quantityProduced;
    private Integer quantityPassed;
    private Integer quantityFailed;
    private String defectType;
    private String severity;
    private String status;
    private String inspector;
    private LocalDateTime checkedAt;
    private String comments;
}