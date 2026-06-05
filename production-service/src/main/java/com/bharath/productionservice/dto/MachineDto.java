package com.bharath.productionservice.dto;

public class MachineDto {
    private Long id;
    private String machineName;
    private String machineType;
    private String status;

    public MachineDto() {
    }

    public MachineDto(Long id, String machineName, String machineType, String status) {
        this.id = id;
        this.machineName = machineName;
        this.machineType = machineType;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMachineName() {
        return machineName;
    }

    public void setMachineName(String machineName) {
        this.machineName = machineName;
    }

    public String getMachineType() {
        return machineType;
    }

    public void setMachineType(String machineType) {
        this.machineType = machineType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
