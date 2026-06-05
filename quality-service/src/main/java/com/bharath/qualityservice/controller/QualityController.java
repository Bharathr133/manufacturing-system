package com.bharath.qualityservice.controller;

import com.bharath.qualityservice.dto.QualityRequest;
import com.bharath.qualityservice.dto.QualityResponse;
import com.bharath.qualityservice.entity.QualityCheck;
import com.bharath.qualityservice.repository.QualityCheckRepository;
import com.bharath.qualityservice.service.QualityService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/quality")
@RequiredArgsConstructor
public class QualityController {

    private final QualityService qualityService;
    private final QualityCheckRepository repository;  // ← ADD THIS LINE

    @PostMapping("/check")
    public ResponseEntity<QualityResponse> recordQualityCheck(@Valid @RequestBody QualityRequest request) {
        QualityResponse response = qualityService.recordQualityCheck(request);
        return ResponseEntity.ok(response);
    }

    // ========== ADD THESE 2 NEW METHODS ==========

    @GetMapping("/all")
    public ResponseEntity<List<QualityCheck>> getAllQualityChecks() {
        return ResponseEntity.ok(repository.findAll());
    }
    // Get checks by machine ID
    @GetMapping("/machine/{machineId}")
    public ResponseEntity<List<QualityCheck>> getChecksByMachine(@PathVariable Long machineId) {
        return ResponseEntity.ok(repository.findByMachineId(machineId));
    }
    @GetMapping("/stats")
    public ResponseEntity<Map<String, Object>> getStats() {
        List<QualityCheck> all = repository.findAll();

        long totalChecks = all.size();
        long passed = all.stream().filter(q -> "PASSED".equals(q.getStatus())).count();
        long rework = all.stream().filter(q -> "REWORK_NEEDED".equals(q.getStatus())).count();
        long failed = all.stream().filter(q -> "FAILED".equals(q.getStatus())).count();

        int totalFailedUnits = all.stream().mapToInt(QualityCheck::getQuantityFailed).sum();
        int totalProducedUnits = all.stream().mapToInt(QualityCheck::getQuantityProduced).sum();
        double defectRate = totalProducedUnits > 0 ? (totalFailedUnits * 100.0 / totalProducedUnits) : 0;

        Map<String, Object> stats = new HashMap<>();
        stats.put("totalChecks", totalChecks);
        stats.put("passed", passed);
        stats.put("rework", rework);
        stats.put("failed", failed);
        stats.put("defectRate", defectRate);

        return ResponseEntity.ok(stats);
    }
}