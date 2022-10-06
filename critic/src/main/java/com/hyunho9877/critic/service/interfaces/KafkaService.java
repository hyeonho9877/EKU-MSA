package com.hyunho9877.critic.service.interfaces;

import com.hyunho9877.critic.dto.CriticDTO;

public interface KafkaService {
    void send(String topic, CriticDTO criticDTO);
}
