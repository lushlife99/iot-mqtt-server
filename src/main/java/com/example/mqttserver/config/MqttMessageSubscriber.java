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

    private final static String TOPIC_HEADER = "mqtt_receivedTopic";
    private final static String CHANGE_METHOD_VALUE = "status";
    private final CabinetSubHandler cabinetSubHandler;

    public MqttMessageSubscriber(CabinetSubHandler cabinetSubHandler) {
        this.cabinetSubHandler = cabinetSubHandler;
    }

    @Override
    public void handleMessage(Message<?> message) throws MessagingException {
        log.info("time = {}", LocalDateTime.now());
        log.info("message arrived. message = {}", message.toString());

        MessageHeaders headers = message.getHeaders();
        String topic = headers.get(TOPIC_HEADER).toString();
        String[] topicParts = topic.split("/");
        if (topicParts.length != 4) {
            System.out.println(topicParts.length);
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