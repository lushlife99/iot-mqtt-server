package com.example.mqttserver.service;

import com.example.mqttserver.config.MqttBrokerConfig;
import com.example.mqttserver.enums.CabinetStatus;
import com.example.mqttserver.model.Cabinet;
import com.example.mqttserver.repository.CabinetRepository;
import com.example.mqttserver.session.SessionManager;
import jakarta.servlet.http.HttpServletRequest;
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
    private final String TOPIC_PREFIX = "/topic/";
    private final CabinetRepository cabinetRepository;

    public Cabinet changeToOpen(HttpServletRequest request) throws InterruptedException, BadRequestException {
        Cabinet cabinet = sessionManager.getSession(request);

        publishStatus(cabinet.getId(), CabinetStatus.OPEN);
        Thread.sleep(3000);

        return cabinetRepository.findById(cabinet.getId()).orElseThrow(BadRequestException::new);
    }

    public Cabinet changeToClose(HttpServletRequest request) throws InterruptedException, BadRequestException {
        Cabinet cabinet = sessionManager.getSession(request);

        publishStatus(cabinet.getId(), CabinetStatus.CLOSE);
        Thread.sleep(3000);

        return cabinetRepository.findById(cabinet.getId()).orElseThrow(BadRequestException::new);
    }

    private void publishStatus(Long cabinetId, CabinetStatus cabinetStatus) {
        try {
            MqttClient mqttClient = new MqttClient(MqttBrokerConfig.BROKER_URL, UUID.randomUUID().toString());
            mqttClient.connect();

            MqttMessage mqttMessage = new MqttMessage();
            mqttMessage.setPayload(cabinetStatus.toString().getBytes());
            mqttClient.publish(TOPIC_PREFIX + cabinetId, mqttMessage);
            mqttClient.disconnect();
        } catch (MqttException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}