package com.hyunho9877.critic.utils.kafka;

import com.hyunho9877.critic.repository.CriticRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
@Transactional
@RequiredArgsConstructor
public class KafkaConsumer {

    private final String kafkaTopic = "user-withdraw";
    private final String groupId = "critic";
    private final CriticRepository criticRepository;

    @KafkaListener(topics = kafkaTopic, groupId = groupId)
    public void updateDeletedUserArticle(@Payload String username) {
        log.info("kafka message : {}", username);
        criticRepository.deleteByWriter(username);
    }
}
