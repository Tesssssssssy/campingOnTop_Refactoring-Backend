package com.example.campingontop.queue.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaListenerService {
    private final QueueService queueService;

    @KafkaListener(topics = "reservationTopic", groupId = "reservation-group")
    public void listen(String message) {
        System.out.println("받은 메세지: " + message);
        queueService.enqueue(message);
    }
}
