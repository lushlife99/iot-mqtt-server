package com.example.mqttserver.model;

import com.example.mqttserver.enums.CabinetStatus;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Cabinet implements Serializable {

    @Id
    @GeneratedValue
    private long id;
    private String password;
    @Enumerated
    private CabinetStatus status; // 문 개폐상태
    private LocalDateTime lastModifyTime;
    @OneToMany(fetch = FetchType.LAZY) @Builder.Default
    private List<CabinetLog> cabinetLogList = new ArrayList<>();
}