package com.spread.it.mqtt.publish;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@MapperScan(basePackages = "com.spread.it.mqtt.publish.mapper")
public class MqttPublishServer {

    public static void main(String[] args) {
        SpringApplication.run(MqttPublishServer.class, args);
    }

}
