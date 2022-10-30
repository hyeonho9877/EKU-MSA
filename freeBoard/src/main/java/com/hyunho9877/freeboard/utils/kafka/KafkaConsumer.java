package com.hyunho9877.freeboard.utils.kafka;

import com.hyunho9877.freeboard.repository.FreeBoardCommentRepository;
import com.hyunho9877.freeboard.repository.FreeBoardRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaConsumer {

    private final String kafkaTopic = "account-freeboard-topic";
    private final String groupId = "freeboard";
    private final FreeBoardRepository boardRepository;
    private final FreeBoardCommentRepository commentRepository;

    @KafkaListener(topics = kafkaTopic, groupId = groupId)
    public void updateDeletedUserArticle(@Payload String username) {
        boardRepository.deleteByWriter(username);
        commentRepository.deleteByWriter(username);
    }
}
