package com.hyunho9877.auth.service;

import com.hyunho9877.auth.enums.KafkaTopics;
import com.hyunho9877.auth.service.interfaces.KafkaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.EnumSet;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class KafkaServiceImpl implements KafkaService {

    private final KafkaTemplate<String, String> kafkaTemplate;

    @Override
    public void send(String username, EnumSet<KafkaTopics> topics) {
        log.info("user id : {}", username);
        topics.forEach(topic -> {
            log.info("topic : {}", topic.getTopicName());
             kafkaTemplate.send(topic.getTopicName(), "username", username);
        });
    }
}
