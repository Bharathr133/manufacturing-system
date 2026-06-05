package com.bharath.machineservice.controller;

import com.bharath.machineservice.dto.MachineDTO;
import com.bharath.machineservice.dto.MachineStatusRequest;
import com.bharath.machineservice.entity.Machine;
import com.bharath.machineservice.service.MachineService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/machines")
public class MachineController {

    private final MachineService machineService;

    // Explicit constructor
    public MachineController(MachineService machineService) {
        this.machineService = machineService;
    }

    @PostMapping
    public ResponseEntity<MachineDTO> createMachine(@Valid @RequestBody Machine machine) {
        return ResponseEntity.status(HttpStatus.CREATED).body(machineService.createMachine(machine));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MachineDTO> getMachine(@PathVariable Long id) {
        return ResponseEntity.ok(machineService.getMachine(id));
    }

    @GetMapping
    public ResponseEntity<List<MachineDTO>> getAllMachines() {
        return ResponseEntity.ok(machineService.getAllMachines());
    }

    @GetMapping("/all")
    public ResponseEntity<List<MachineDTO>> getAllMachinesAlias() {
        return getAllMachines();
    }

    @PutMapping("/{id}")
    public ResponseEntity<MachineDTO> updateMachine(
            @PathVariable Long id,
            @Valid @RequestBody Machine machine
    ) {
        return ResponseEntity.ok(machineService.updateMachine(id, machine));
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<MachineDTO> updateMachineStatus(
            @PathVariable Long id,
            @Valid @RequestBody MachineStatusRequest request
    ) {
        return ResponseEntity.ok(machineService.updateMachineStatus(id, request.status()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMachine(@PathVariable Long id) {
        machineService.deleteMachine(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/count")
    public ResponseEntity<Map<String, Long>> getMachineCount() {
        Map<String, Long> counts = new HashMap<>();
        counts.put("total", machineService.getMachineCount());
        counts.put("running", machineService.getRunningCount());
        return ResponseEntity.ok(counts);
    }

    @GetMapping("/running")
    public ResponseEntity<Long> getRunningMachinesCount() {
        return ResponseEntity.ok(machineService.getRunningCount());
    }

    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("Machine Service is running");
    }
}