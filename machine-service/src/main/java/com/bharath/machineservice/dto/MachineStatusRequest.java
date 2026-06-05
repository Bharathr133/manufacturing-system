package com.bharath.machineservice.dto;

public class MachineStatusRequest {
    private String status;

    public MachineStatusRequest() {}

    public MachineStatusRequest(String status) {
        this.status = status;
    }

    public String status() { return status; }
    public void setStatus(String status) { this.status = status; }
}