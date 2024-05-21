package com.example.mqttserver.config;

import com.example.mqttserver.enums.CabinetStatus;
import com.example.mqttserver.service.CabinetControlService;
import com.example.mqttserver.service.CabinetSubHandler;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.MessagingException;

public class MqttMessageSubscriber implements MessageHandler {

    private final static String CLIENT_ID_HEADER = "id";
    private final static String TOPIC_HEADER = "mqtt_receivedTopic";
    private final static String CHANGE_METHOD_VALUE = "change_status";
    private final CabinetSubHandler cabinetSubHandler;

    public MqttMessageSubscriber(CabinetSubHandler cabinetSubHandler) {
        this.cabinetSubHandler = cabinetSubHandler;
    }

    @Override
    public void handleMessage(Message<?> message) throws MessagingException {
        MessageHeaders headers = message.getHeaders();
        Long clientId = Long.parseLong(headers.get(CLIENT_ID_HEADER).toString());
        String topic = headers.get(TOPIC_HEADER).toString();
        CabinetStatus cabinetStatus = CabinetStatus.valueOf(message.getPayload().toString());
        if(topic.equals(CHANGE_METHOD_VALUE)) {
            try {
                cabinetSubHandler.updateStatus(clientId, cabinetStatus);
            } catch (BadRequestException e) {
                throw new RuntimeException(e);
            }
        }

    }
}