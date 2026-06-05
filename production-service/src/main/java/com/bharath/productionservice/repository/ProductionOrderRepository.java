package com.bharath.productionservice.repository;

import com.bharath.productionservice.entity.ProductionOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface ProductionOrderRepository extends JpaRepository<ProductionOrder, Long> {
    Optional<ProductionOrder> findByMachineIdAndStatus(Long machineId, String status);
    List<ProductionOrder> findByStatus(String status);
    boolean existsByMachineIdAndStatus(Long machineId, String status);
}