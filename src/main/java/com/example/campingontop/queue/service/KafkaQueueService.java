package com.example.campingontop.queue.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaQueueService {
    private final KafkaTemplate<String, String> kafkaTemplate;

    public void sendMessage(String userId) {
        kafkaTemplate.send("reservationTopic", userId);
    }
}
