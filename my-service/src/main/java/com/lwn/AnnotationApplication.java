package com.lwn;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * @author liwannian
 * @date 2020年9月11日17点58分17点58分
 */
@SpringBootApplication(scanBasePackages = "com.lwn")
@MapperScan(basePackages = "com.lwn.model.mapper")
@EnableAsync
public class AnnotationApplication {

    public static void main(String[] args) {
        SpringApplication.run(AnnotationApplication.class, args);
    }

}
