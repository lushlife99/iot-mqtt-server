package com.example.mqttserver.controller;

import com.example.mqttserver.model.Cabinet;
import com.example.mqttserver.model.CabinetLog;
import com.example.mqttserver.service.CabinetService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class SmartCabinetController {

    private final CabinetService cabinetService;

    @GetMapping(value = {"/login", "/"})
    public String loginPage() {
        return "login";
    }

    @GetMapping("/main")
    public String mainPage(Model model, HttpServletRequest request) throws BadRequestException {
        Cabinet cabinet = cabinetService.getCabinet(request);
        List<CabinetLog> cabinetLogs = cabinetService.getCabinetLogs(request);

        model.addAttribute("cabinet", cabinet);
        model.addAttribute("cabinetLogs", cabinetLogs);
        return "main";
    }

}
