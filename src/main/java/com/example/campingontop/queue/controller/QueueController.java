package com.example.campingontop.queue.controller;

import com.example.campingontop.queue.service.KafkaQueueService;
import com.example.campingontop.queue.service.QueueService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@Slf4j
public class QueueController {
    private final QueueService queueService;
    private final KafkaQueueService kafkaQueueService;
    private static final int MAX_CONCURRENT_USERS = 2;

    @GetMapping("/joinQueue")
    public ResponseEntity<?> joinQueue(HttpServletRequest request) {
        String ip = request.getRemoteAddr();
        long queueSize = queueService.queueSize();

        if (queueSize >= MAX_CONCURRENT_USERS) {
            kafkaQueueService.sendMessage(ip);
            return ResponseEntity.ok("대기열 접속 완료. 현재 순번: " + (queueSize + 1));
        } else {
            return ResponseEntity.ok("지금 바로 접속할 수 있습니다.");
        }
    }

    @GetMapping("/checkQueue")
    public ResponseEntity<?> checkQueueStatus(HttpServletRequest request) {
        String ip = request.getRemoteAddr();
        int position = queueService.findPosition(ip);

        if (position == -1) {
            return ResponseEntity.ok(Map.of("inQueue", false));
        } else {
            return ResponseEntity.ok(Map.of("inQueue", true, "position", position));
        }
    }
}
