package com.bharath.productionservice.controller;

import com.bharath.productionservice.dto.ProductionRequest;
import com.bharath.productionservice.dto.ProductionResponse;
import com.bharath.productionservice.entity.ProductionOrder;
import com.bharath.productionservice.service.ProductionService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/production")
public class ProductionController {

    private final ProductionService productionService;

    public ProductionController(ProductionService productionService) {
        this.productionService = productionService;
    }

    @GetMapping
    public List<ProductionOrder> getAllOrders() {
        return productionService.getAllOrders();
    }

    @GetMapping("/{id}")
    public ProductionOrder getOrder(
            @PathVariable Long id) {

        return productionService.getOrder(id);
    }

    @PostMapping("/{machineId}")
    public ProductionResponse startProduction(
            @PathVariable Long machineId,
            @RequestBody ProductionRequest request) {

        return productionService
                .startProduction(
                        machineId,
                        request);
    }

    @PutMapping("/{orderId}/complete")
    public ProductionResponse completeOrder(@PathVariable Long orderId) {
        return productionService.completeOrder(orderId);
    }

    @PutMapping("/complete/{orderId}")
    public ProductionResponse completeOrderLegacy(@PathVariable Long orderId) {
        return productionService.completeOrder(orderId);
    }

    @GetMapping("/active")
    public List<ProductionOrder> getActiveOrders() {
        return productionService.getActiveOrders();
    }

    @GetMapping("/count")
    public long getOrderCount() {
        return productionService
                .getOrderCount();
    }
}
