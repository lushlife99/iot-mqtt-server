package com.example.mqttserver.service;

import com.example.mqttserver.enums.CabinetStatus;
import com.example.mqttserver.model.Cabinet;
import com.example.mqttserver.model.CabinetLog;
import com.example.mqttserver.repository.CabinetLogRepository;
import com.example.mqttserver.repository.CabinetRepository;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CabinetSubHandler {

    private final CabinetRepository cabinetRepository;
    private final CabinetLogRepository cabinetLogRepository;

    public void updateStatus(Long id, CabinetStatus cabinetStatus) throws BadRequestException {
        Cabinet cabinet = cabinetRepository.findById(id).orElseThrow(BadRequestException::new);
        cabinet.setStatus(cabinetStatus);
        cabinet.setLastModifyTime(LocalDateTime.now());

        CabinetLog cabinetLog = CabinetLog.builder()
                .cabinet(cabinet)
                .updatedStatus(cabinetStatus)
                .updateTime(LocalDateTime.now())
                .build();
        cabinetLogRepository.save(cabinetLog);
    }
}