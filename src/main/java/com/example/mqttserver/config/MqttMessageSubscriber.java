package com.example.mqttserver.config;

import com.example.mqttserver.enums.CabinetStatus;
import com.example.mqttserver.error.CustomException;
import com.example.mqttserver.error.ErrorCode;
import com.example.mqttserver.service.CabinetSubHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.MessagingException;

import java.time.LocalDateTime;

@Slf4j
public class MqttMessageSubscriber implements MessageHandler {

    private static final String CLIENT_ID_HEADER = "id";
    private static final String TOPIC_HEADER = "mqtt_receivedTopic";
    private static final String CHANGE_METHOD_VALUE = "status";
    private final CabinetSubHandler cabinetSubHandler;

    public MqttMessageSubscriber(CabinetSubHandler cabinetSubHandler) {
        this.cabinetSubHandler = cabinetSubHandler;
    }

    @Override
    public void handleMessage(Message<?> message) throws MessagingException {
        MessageHeaders headers = message.getHeaders();
        String topic = headers.get(TOPIC_HEADER).toString();

        // Check if the topic matches the desired pattern
        if (!topic.startsWith("/pub/")) {
            log.warn("Message received from an undesired topic: {}", topic);
            return;  // Ignore messages not matching the /pub/# pattern
        }

        log.info("time = {}", LocalDateTime.now());
        log.info("message arrived. message = {}", message.toString());

        String[] topicParts = topic.split("/");
        if (topicParts.length != 4) {
            log.warn("Invalid topic length: {}", topicParts.length);
            throw new CustomException(ErrorCode.BAD_REQUEST);
        }

        Long id = Long.parseLong(topicParts[3]);

        if (topicParts[2].equals(CHANGE_METHOD_VALUE)) {
            CabinetStatus cabinetStatus = CabinetStatus.valueOf(message.getPayload().toString());
            try {
                cabinetSubHandler.updateStatus(id, cabinetStatus);
            } catch (BadRequestException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
