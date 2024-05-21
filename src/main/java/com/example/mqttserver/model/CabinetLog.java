package com.example.mqttserver.model;

import com.example.mqttserver.enums.CabinetStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class CabinetLog {

    @Id @GeneratedValue
    private long id;
    @Enumerated
    private CabinetStatus updatedStatus;
    private LocalDateTime updateTime;
    @ManyToOne
    private Cabinet cabinet;
}