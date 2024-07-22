package com.example.campingontop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableFeignClients
@EnableDiscoveryClient
@EnableScheduling
@EnableJpaAuditing
@EnableJpaRepositories(basePackages = "com.example.campingontop.domain.mysql")  // MySQL repositories
@EnableMongoRepositories(basePackages = "com.example.campingontop.domain.mongodb.chat")  // MongoDB repositories
public class CampingOnTopApplication {

    public static void main(String[] args) {
        SpringApplication.run(CampingOnTopApplication.class, args);
    }
}
