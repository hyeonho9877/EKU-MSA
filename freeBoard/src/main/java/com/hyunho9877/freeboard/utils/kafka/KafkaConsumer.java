package com.hyunho9877.freeboard.utils.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Slf4j
@Transactional
public class KafkaConsumer {
    private final String kafkaTopic = "account-freeboard-topic";

    @KafkaListener(topics = kafkaTopic)
    public void updateDeletedUserArticle(String kafkaMessage) {
        log.info("kafka message received : {}", kafkaMessage);
    }
}
