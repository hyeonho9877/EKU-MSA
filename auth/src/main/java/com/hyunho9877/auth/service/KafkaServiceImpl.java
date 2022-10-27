package com.hyunho9877.auth.service;

import com.hyunho9877.auth.enums.KafkaTopics;
import com.hyunho9877.auth.service.interfaces.KafkaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.EnumSet;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaServiceImpl implements KafkaService {

    private final KafkaTemplate<String, String> kafkaTemplate;

    @Override
    public void send(String userId, EnumSet<KafkaTopics> topics) {
        topics.forEach(topic -> kafkaTemplate.send(topic.getTopicName(), userId));
    }
}
