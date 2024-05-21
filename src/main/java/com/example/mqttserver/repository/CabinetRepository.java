package com.example.mqttserver.repository;

import com.example.mqttserver.model.Cabinet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CabinetRepository extends JpaRepository<Cabinet, Long> {

    Optional<Cabinet> findByIdAndPassword(Long id, String password);
}
