package com.spread.it.mqtt.publish.task;

import com.spread.it.mqtt.publish.service.MQTTServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;

@Component
public class MqttPublishSchedulHandler {
    @Autowired
    private MQTTServiceImpl service;
    @Scheduled(cron = "0 */1 * * * ?")
    public void publish() throws UnsupportedEncodingException {
        service.branch("subaction");
    }
    @Scheduled(cron = "0 */2 * * * ?")
    private void repeat() throws UnsupportedEncodingException {
        service.branch("subaction");
//        service.branch("pubaction");
    }
}
