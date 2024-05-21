package com.example.mqttserver.controller.api;

import com.example.mqttserver.model.Cabinet;
import com.example.mqttserver.service.CabinetService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final CabinetService cabinetService;

    @PostMapping("/user/login")
    public ResponseEntity login(@RequestBody Cabinet cabinet, HttpServletResponse response) throws BadRequestException {
        System.out.println("AuthController.login");
        cabinetService.login(cabinet, response);
        return new ResponseEntity(HttpStatus.OK);
    }
}
