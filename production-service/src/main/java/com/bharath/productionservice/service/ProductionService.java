package com.bharath.productionservice.service;

import com.bharath.productionservice.client.MachineClient;
import com.bharath.productionservice.client.QualityClient;
import com.bharath.productionservice.dto.MachineDto;
import com.bharath.productionservice.dto.ProductionRequest;
import com.bharath.productionservice.dto.ProductionResponse;
import com.bharath.productionservice.dto.QualityRequest;
import com.bharath.productionservice.entity.ProductionOrder;
import com.bharath.productionservice.exception.BusinessException;
import com.bharath.productionservice.repository.ProductionOrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;


@Service
public class ProductionService {

    private static final Logger log = LoggerFactory.getLogger(ProductionService.class);

    private final ProductionOrderRepository repository;
    private final MachineClient machineClient;
    private final QualityClient qualityClient;

    public ProductionService(ProductionOrderRepository repository, MachineClient machineClient, QualityClient qualityClient) {
        this.repository = repository;
        this.machineClient = machineClient;
        this.qualityClient = qualityClient;
    }

    // Start Production
    public ProductionResponse startProduction(Long machineId, ProductionRequest request) {

        // 1. Validate quantity
        if (request.getQuantity() == null || request.getQuantity() < 1 || request.getQuantity() > 1000) {
            throw new BusinessException("Quantity must be between 1 and 1000");
        }

        // 2. Validate part number format
        if (request.getPartNumber() == null || request.getPartNumber().trim().isEmpty()) {
            throw new BusinessException("Part number is required");
        }
        if (request.getPartNumber().length() > 50) {
            throw new BusinessException("Part number cannot exceed 50 characters");
        }

        // 3. Get and validate machine
        MachineDto machine;
        try {
            machine = machineClient.getMachine(machineId);
        } catch (Exception e) {
            throw new BusinessException("Machine service unavailable. Please try again later.");
        }

        if (machine == null) {
            throw new BusinessException("Machine not found with ID: " + machineId);
        }

        // 4. Only ready machines can start production.
        if (!"IDLE".equalsIgnoreCase(machine.getStatus()) && !"RUNNING".equalsIgnoreCase(machine.getStatus())) {
            throw new BusinessException("Machine '" + machine.getMachineName() + "' is not ready for production. Current status: " + machine.getStatus());
        }

        // 5. Check if machine already has active production
        if (repository.existsByMachineIdAndStatus(machineId, "ACTIVE")) {
            throw new BusinessException("Machine '" + machine.getMachineName() + "' already has an active production order. Complete it first.");
        }

        // 6. Starting production puts the machine into RUNNING state.
        try {
            machineClient.updateMachineStatus(machineId, "RUNNING");
        } catch (Exception e) {
            throw new BusinessException("Unable to start machine '" + machine.getMachineName() + "'. Please try again.");
        }

        // 7. Create production order
        ProductionOrder order = new ProductionOrder();
        order.setPartNumber(request.getPartNumber().trim());
        order.setQuantity(request.getQuantity());
        order.setMachineId(machineId);

        ProductionOrder saved = repository.save(order);

        return new ProductionResponse(
                saved.getId(),
                "Production started successfully on machine '" + machine.getMachineName() + "'",
                saved.getPartNumber(),
                saved.getQuantity(),
                saved.getStatus()
        );
    }

    // Complete Production
    public ProductionResponse completeOrder(Long orderId) {
        ProductionOrder order = repository.findById(orderId)
                .orElseThrow(() -> new BusinessException("Production order not found with ID: " + orderId));

        if ("COMPLETED".equals(order.getStatus())) {
            throw new BusinessException("Production order " + orderId + " is already completed");
        }

        if ("CANCELLED".equals(order.getStatus())) {
            throw new BusinessException("Cannot complete a cancelled production order");
        }

        order.setStatus("COMPLETED");
        order.setCompletedAt(LocalDateTime.now());
        repository.save(order);

        try {
            machineClient.updateMachineStatus(order.getMachineId(), "IDLE");
        } catch (Exception e) {
            throw new BusinessException("Production was completed, but machine status could not be reset to IDLE.");
        }

        // Auto-record quality check
        try {
            QualityRequest qualityRequest = new QualityRequest();
            qualityRequest.setProductionOrderId(order.getId());
            qualityRequest.setMachineId(order.getMachineId());
            qualityRequest.setPartNumber(order.getPartNumber());
            qualityRequest.setQuantityProduced(order.getQuantity());
            qualityRequest.setQuantityPassed(order.getQuantity());
            qualityRequest.setDefectType("NONE");
            qualityRequest.setSeverity("MINOR");
            qualityRequest.setInspector("SYSTEM");
            qualityRequest.setComments("Auto-recorded on completion - assumes all units passed");

            qualityClient.recordQualityCheck(qualityRequest);
            log.info("✅ Auto quality check recorded for order: {}", order.getId());
        } catch (Exception e) {
            log.warn("⚠️ Could not auto-record quality check: {}", e.getMessage());
        }

        return new ProductionResponse(
                order.getId(),
                "Completed order #" + order.getId() + ". Produced " + order.getQuantity() + " units of " + order.getPartNumber() + ".",
                order.getPartNumber(),
                order.getQuantity(),
                "COMPLETED"
        );
    }


    // Get All Orders
    public List<ProductionOrder> getAllOrders() {
        return repository.findAll();
    }

    // Get Active Orders
    public List<ProductionOrder> getActiveOrders() {
        return repository.findByStatus("ACTIVE");
    }

    public long getOrderCount() {
        return repository.count();
    }

    // Get Order by ID
    public ProductionOrder getOrder(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new BusinessException("Order not found with ID: " + id));
    }
}
