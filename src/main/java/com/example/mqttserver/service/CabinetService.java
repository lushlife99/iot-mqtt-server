package com.example.mqttserver.service;

import com.example.mqttserver.enums.CabinetStatus;
import com.example.mqttserver.model.Cabinet;
import com.example.mqttserver.model.CabinetLog;
import com.example.mqttserver.repository.CabinetLogRepository;
import com.example.mqttserver.repository.CabinetRepository;
import com.example.mqttserver.session.SessionManager;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CabinetService {

    private final SessionManager sessionManager;
    private final CabinetLogRepository cabinetLogRepository;
    private final CabinetRepository cabinetRepository;

    public void login(Cabinet cabinet, HttpServletResponse response) throws BadRequestException {
        cabinet = cabinetRepository.findByIdAndPassword(cabinet.getId(), cabinet.getPassword()).orElseThrow(BadRequestException::new);
        sessionManager.createSession(cabinet, response);
    }

    public Cabinet getCabinet(HttpServletRequest request) throws BadRequestException {
        return sessionManager.getSession(request);
    }
    public List<CabinetLog> getCabinetLogs(HttpServletRequest request) throws BadRequestException {
        Cabinet cabinet = sessionManager.getSession(request);
        return cabinetLogRepository.findTop10ByCabinetIdOrderByUpdateTimeDesc(cabinet.getId());
    }

}
