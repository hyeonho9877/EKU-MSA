package com.hyunho9877.auth.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum KafkaTopics {

    FREEBOARD("account-freeboard-topic"),
    INFOBOARD("account-infoboard-topic"),
    DOODLE("account-doodle-topic");

    private final String topicName;
}
