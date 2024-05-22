package com.example.mqttserver.controller.api;

import com.example.mqttserver.dto.CabinetDto;
import com.example.mqttserver.model.Cabinet;
import com.example.mqttserver.model.CabinetLog;
import com.example.mqttserver.service.CabinetControlService;
import com.example.mqttserver.service.CabinetService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cabinet")
public class SmartCabinetApiController {

    private final CabinetControlService cabinetControlService;
    private final CabinetService cabinetService;

    @PutMapping("/open")
    public CabinetDto openCabinet(HttpServletRequest request) throws InterruptedException {
        return cabinetControlService.changeToOpen(request);
    }

    @PutMapping("/close")
    public CabinetDto closeCabinet(HttpServletRequest request) throws InterruptedException {
        return cabinetControlService.changeToClose(request);
    }

    @GetMapping
    public Cabinet getCabinet(HttpServletRequest request) throws BadRequestException {
        return cabinetService.getCabinet(request);
    }

    @GetMapping("/logs")
    public List<CabinetLog> getCabinetLogs(HttpServletRequest request) throws BadRequestException {
        return cabinetService.getCabinetLogs(request);
    }

}
