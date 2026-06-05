package com.bharath.machineservice.dto;

import java.time.LocalDateTime;

public class MachineDTO {
    private Long id;
    private String machineName;
    private String machineType;
    private String status;
    private LocalDateTime createdAt;

    public MachineDTO() {}

    public MachineDTO(Long id, String machineName, String machineType, String status, LocalDateTime createdAt) {
        this.id = id;
        this.machineName = machineName;
        this.machineType = machineType;
        this.status = status;
        this.createdAt = createdAt;
    }

    public Long getId() { return id; }
    public String getMachineName() { return machineName; }
    public String getMachineType() { return machineType; }
    public String getStatus() { return status; }
    public LocalDateTime getCreatedAt() { return createdAt; }

    public void setId(Long id) { this.id = id; }
    public void setMachineName(String machineName) { this.machineName = machineName; }
    public void setMachineType(String machineType) { this.machineType = machineType; }
    public void setStatus(String status) { this.status = status; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}