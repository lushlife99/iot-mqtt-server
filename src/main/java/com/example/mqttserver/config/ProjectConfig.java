package com.example.mqttserver.config;

import com.example.mqttserver.enums.CabinetStatus;
import com.example.mqttserver.error.CustomException;
import com.example.mqttserver.error.ErrorCode;
import com.example.mqttserver.model.Cabinet;
import com.example.mqttserver.model.CabinetLog;
import com.example.mqttserver.repository.CabinetLogRepository;
import com.example.mqttserver.repository.CabinetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;

import java.time.LocalDateTime;
import java.util.Optional;

@Configuration
@RequiredArgsConstructor
public class ProjectConfig {

    private final CabinetRepository cabinetRepository;
    private final CabinetLogRepository cabinetLogRepository;

    @EventListener(ApplicationReadyEvent.class)
    public void init() {
        Optional<Cabinet> optionalCabinet = cabinetRepository.findById(1L);
        Cabinet cabinet;
        if(optionalCabinet.isEmpty()) {
            cabinet = Cabinet.builder()
                    .lastModifyTime(LocalDateTime.now())
                    .status(CabinetStatus.CLOSE)
                    .password("1234")
                    .build();

            cabinet = cabinetRepository.save(cabinet);
        } else {
            cabinet = optionalCabinet.get();
        }

        Optional<CabinetLog> optionalCabinetLog = cabinetLogRepository.findById(1L);
        if(optionalCabinetLog.isEmpty()) {
            CabinetLog cabinetLog = CabinetLog.builder()
                    .cabinet(cabinet)
                    .updatedStatus(CabinetStatus.CLOSE)
                    .updateTime(LocalDateTime.now())
                    .build();

            cabinetLogRepository.save(cabinetLog);
        }
    }
}
