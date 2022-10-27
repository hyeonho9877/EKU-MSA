package com.hyunho9877.auth.service.interfaces;


import com.hyunho9877.auth.enums.KafkaTopics;

import java.util.EnumSet;

public interface KafkaService {
    void send(String userId, EnumSet<KafkaTopics> topics);
}
