package com.example.mqttserver.dto;

import com.example.mqttserver.enums.CabinetStatus;
import com.example.mqttserver.model.Cabinet;
import com.example.mqttserver.model.CabinetLog;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CabinetDto {

    private long id;
    private CabinetStatus status;
    private LocalDateTime lastModifyTime;

    public CabinetDto(Cabinet cabinet) {
        this.id = cabinet.getId();
        this.status = cabinet.getStatus();
        this.lastModifyTime = cabinet.getLastModifyTime();
    }
}
