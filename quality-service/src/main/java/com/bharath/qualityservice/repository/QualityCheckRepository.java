package com.bharath.qualityservice.repository;

import com.bharath.qualityservice.entity.QualityCheck;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface QualityCheckRepository extends JpaRepository<QualityCheck, Long> {
    List<QualityCheck> findByProductionOrderId(Long orderId);
    List<QualityCheck> findByMachineId(Long machineId);
    List<QualityCheck> findByStatus(String status);
}