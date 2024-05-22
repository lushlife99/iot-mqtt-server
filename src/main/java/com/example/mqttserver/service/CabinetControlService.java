package com.example.mqttserver.service;

import com.example.mqttserver.config.MqttBrokerConfig;
import com.example.mqttserver.dto.CabinetDto;
import com.example.mqttserver.enums.CabinetStatus;
import com.example.mqttserver.error.CustomException;
import com.example.mqttserver.error.ErrorCode;
import com.example.mqttserver.model.Cabinet;
import com.example.mqttserver.repository.CabinetRepository;
import com.example.mqttserver.session.SessionManager;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CabinetControlService {

    private final SessionManager sessionManager;
    private final String CHANGE_COMMAND = "CHANGE";
    private final String QUEUE_PREFIX = "/queue/";
    private final CabinetRepository cabinetRepository;
    private final int WAIT_TIME = 3000;

    @Transactional
    public CabinetDto changeToOpen(HttpServletRequest request) throws InterruptedException {
        Cabinet cabinet = sessionManager.getSession(request);

        publishStatus(cabinet.getId(), CabinetStatus.OPEN);
        Thread.sleep(WAIT_TIME);

        CabinetDto cabinetDto = new CabinetDto(cabinetRepository.findById(cabinet.getId()).orElseThrow(() -> new CustomException(ErrorCode.BAD_REQUEST)));
        if(cabinetDto.getStatus() == CabinetStatus.CLOSE)
            throw new CustomException(ErrorCode.STATUS_NOT_CHANGED);
        return cabinetDto;
    }

    @Transactional
    public CabinetDto changeToClose(HttpServletRequest request) throws InterruptedException {
        Cabinet cabinet = sessionManager.getSession(request);

        publishStatus(cabinet.getId(), CabinetStatus.CLOSE);
        Thread.sleep(WAIT_TIME);
        CabinetDto cabinetDto = new CabinetDto(cabinetRepository.findById(cabinet.getId()).orElseThrow(() -> new CustomException(ErrorCode.BAD_REQUEST)));
        if(cabinetDto.getStatus() == CabinetStatus.OPEN)
            throw new CustomException(ErrorCode.STATUS_NOT_CHANGED);
        return cabinetDto;
    }

    private void publishStatus(Long cabinetId, CabinetStatus cabinetStatus) {
        try {
            MqttClient mqttClient = new MqttClient(MqttBrokerConfig.BROKER_URL, UUID.randomUUID().toString());
            mqttClient.connect();

            MqttMessage mqttMessage = new MqttMessage();
            mqttMessage.setPayload(cabinetStatus.toString().getBytes());
            mqttClient.publish(QUEUE_PREFIX + cabinetId, mqttMessage);
            mqttClient.disconnect();
        } catch (MqttException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}