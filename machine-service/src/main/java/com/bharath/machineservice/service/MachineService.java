package com.bharath.machineservice.service;

import com.bharath.machineservice.dto.MachineDTO;
import com.bharath.machineservice.entity.Machine;
import com.bharath.machineservice.exception.BusinessException;
import com.bharath.machineservice.repository.MachineRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MachineService {

    private final MachineRepository machineRepository;

    // Add this constructor explicitly
    public MachineService(MachineRepository machineRepository) {
        this.machineRepository = machineRepository;
    }

    private MachineDTO convertToDTO(Machine machine) {
        return new MachineDTO(
                machine.getId(),
                machine.getMachineName(),
                machine.getMachineType(),
                machine.getStatus(),
                machine.getCreatedAt()
        );
    }

    public MachineDTO createMachine(Machine machine) {
        if (machineRepository.existsByMachineName(machine.getMachineName())) {
            throw new BusinessException("Machine name already exists: " + machine.getMachineName());
        }
        machine.setCreatedAt(LocalDateTime.now());
        Machine saved = machineRepository.save(machine);
        return convertToDTO(saved);
    }

    public MachineDTO getMachine(Long id) {
        Machine machine = machineRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Machine not found with ID: " + id));
        return convertToDTO(machine);
    }

    public List<MachineDTO> getAllMachines() {
        return machineRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public MachineDTO updateMachine(Long id, Machine machineDetails) {
        Machine existing = machineRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Machine not found with ID: " + id));

        existing.setMachineName(machineDetails.getMachineName());
        existing.setMachineType(machineDetails.getMachineType());
        existing.setStatus(machineDetails.getStatus());

        Machine updated = machineRepository.save(existing);
        return convertToDTO(updated);
    }

    public MachineDTO updateMachineStatus(Long id, String status) {
        Machine existing = machineRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Machine not found with ID: " + id));

        existing.setStatus(status);
        Machine updated = machineRepository.save(existing);
        return convertToDTO(updated);
    }

    public void deleteMachine(Long id) {
        if (!machineRepository.existsById(id)) {
            throw new BusinessException("Machine not found with ID: " + id);
        }
        machineRepository.deleteById(id);
    }

    public long getMachineCount() {
        return machineRepository.count();
    }

    public long getRunningCount() {
        return machineRepository.countByStatus("RUNNING");
    }
}