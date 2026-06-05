package com.bharath.machineservice.repository;

import com.bharath.machineservice.entity.Machine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MachineRepository extends JpaRepository<Machine, Long> {
    boolean existsByMachineName(String machineName);
    long countByStatus(String status);
}