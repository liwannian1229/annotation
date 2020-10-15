package com.lwn.schedule.service;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(scanBasePackages = "com.lwn")
@MapperScan(basePackages = "com.lwn.repo.model.mapper")
@EnableAsync
@EnableScheduling
public class ScheduleServiceApp {
    public static void main(String[] args) {

        SpringApplication.run(ScheduleServiceApp.class, args);
    }
}
