package com.example.mqttserver.repository;

import com.example.mqttserver.model.CabinetLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CabinetLogRepository extends JpaRepository<CabinetLog, Long> {
    List<CabinetLog> findTop10ByCabinetIdOrderByUpdateTimeDesc(long cabinetId);

}
