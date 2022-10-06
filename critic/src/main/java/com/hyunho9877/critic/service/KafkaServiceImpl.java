package com.hyunho9877.critic.service;

import com.google.gson.Gson;
import com.hyunho9877.critic.dto.CriticDTO;
import com.hyunho9877.critic.service.interfaces.KafkaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaServiceImpl implements KafkaService {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final Gson gson = new Gson();

    @Override
    public void send(String topic, CriticDTO criticDTO) {
        String message = gson.toJson(criticDTO);
        kafkaTemplate.send(topic, message);
        log.info("send {} to {}", message, topic);
    }
}
