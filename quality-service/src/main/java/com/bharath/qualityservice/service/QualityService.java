package com.bharath.qualityservice.service;

import com.bharath.qualityservice.dto.QualityRequest;
import com.bharath.qualityservice.dto.QualityResponse;
import com.bharath.qualityservice.entity.QualityCheck;
import com.bharath.qualityservice.repository.QualityCheckRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class QualityService {

    private final QualityCheckRepository repository;

    public QualityResponse recordQualityCheck(QualityRequest request) {
        log.info("Recording quality check for order: {}", request.getProductionOrderId());

        QualityCheck check = new QualityCheck();
        check.setProductionOrderId(request.getProductionOrderId());
        check.setMachineId(request.getMachineId());
        check.setPartNumber(request.getPartNumber());
        check.setQuantityProduced(request.getQuantityProduced());
        check.setQuantityPassed(request.getQuantityPassed());
        check.setQuantityFailed(request.getQuantityProduced() - request.getQuantityPassed());
        check.setDefectType(request.getDefectType());
        check.setSeverity(request.getSeverity());
        check.setInspector(request.getInspector());
        check.setCheckedAt(LocalDateTime.now());
        check.setComments(request.getComments());

        // Determine status
        if (check.getQuantityFailed() == 0) {
            check.setStatus("PASSED");
        } else if (check.getQuantityFailed() < check.getQuantityProduced() / 2) {
            check.setStatus("REWORK_NEEDED");
        } else {
            check.setStatus("FAILED");
        }

        QualityCheck saved = repository.save(check);

        return new QualityResponse(
                saved.getId(),
                saved.getStatus(),
                saved.getQuantityPassed(),
                saved.getQuantityFailed(),
                "Quality check recorded successfully"
        );
    }
}