package com.example.mqttserver.config;

import com.example.mqttserver.enums.CabinetStatus;
import com.example.mqttserver.model.Cabinet;
import com.example.mqttserver.model.CabinetLog;
import com.example.mqttserver.repository.CabinetLogRepository;
import com.example.mqttserver.repository.CabinetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;

import java.time.LocalDateTime;

@Configuration
@RequiredArgsConstructor
public class ProjectConfig {

    private final CabinetRepository cabinetRepository;
    private final CabinetLogRepository cabinetLogRepository;

    @EventListener(ApplicationReadyEvent.class)
    public void init() {
        Cabinet cabinet = Cabinet.builder()
                .lastModifyTime(LocalDateTime.now())
                .status(CabinetStatus.CLOSE)
                .password("1234")
                .build();

        cabinet = cabinetRepository.save(cabinet);

        CabinetLog cabinetLog = CabinetLog.builder()
                .cabinet(cabinet)
                .updatedStatus(CabinetStatus.CLOSE)
                .updateTime(LocalDateTime.now())
                .build();

        cabinetLogRepository.save(cabinetLog);
    }
}
