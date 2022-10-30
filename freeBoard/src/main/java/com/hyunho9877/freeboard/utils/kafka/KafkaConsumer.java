package com.hyunho9877.freeboard.utils.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaConsumer {
    private final String kafkaTopic = "account-freeboard-topic";
    private final String groupId = "freeboard";

    @KafkaListener(topics = kafkaTopic, groupId = groupId)
    public void updateDeletedUserArticle(@Payload String kafkaMessage) {
        log.info("kafka message received : {}", kafkaMessage);
    }
}
