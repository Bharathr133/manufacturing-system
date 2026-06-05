package com.bharath.machineservice.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "machine")
public class Machine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime createdAt;
    private String machineName;
    private String machineType;
    private String status;

    // No-args constructor (required by JPA)
    public Machine() {}

    // All-args constructor
    public Machine(Long id, LocalDateTime createdAt, String machineName, String machineType, String status) {
        this.id = id;
        this.createdAt = createdAt;
        this.machineName = machineName;
        this.machineType = machineType;
        this.status = status;
    }

    // Getters
    public Long getId() { return id; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public String getMachineName() { return machineName; }
    public String getMachineType() { return machineType; }
    public String getStatus() { return status; }

    // Setters
    public void setId(Long id) { this.id = id; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public void setMachineName(String machineName) { this.machineName = machineName; }
    public void setMachineType(String machineType) { this.machineType = machineType; }
    public void setStatus(String status) { this.status = status; }
}