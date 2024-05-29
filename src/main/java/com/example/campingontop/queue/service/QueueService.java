package com.example.campingontop.queue.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class QueueService {
    private final RedisTemplate<String, String> redisTemplate;
    private static final String QUEUE_KEY = "reservationQueue";

    public void enqueue(String userId) {
        redisTemplate.opsForList().rightPush(QUEUE_KEY, userId);
    }

    public String dequeue() {
        return redisTemplate.opsForList().leftPop(QUEUE_KEY);
    }

    public long queueSize() {
        return redisTemplate.opsForList().size(QUEUE_KEY);
    }

    public int findPosition(String userId) {
        List<String> queue = redisTemplate.opsForList().range(QUEUE_KEY, 0, -1);
        if (queue != null) {
            int index = queue.indexOf(userId);
            if (index != -1) {
                return index + 1; // 인덱스는 0부터 시작하므로 +1
            }
        }
        return -1; // 대기열에 없음
    }
}
