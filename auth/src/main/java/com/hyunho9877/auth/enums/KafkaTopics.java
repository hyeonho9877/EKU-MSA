package com.hyunho9877.auth.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum KafkaTopics {

    WITHDRAW("user-withdraw");

    private final String topicName;
}
