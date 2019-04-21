package com.spread.it.mqtt.publish.config;

import com.spread.it.mqtt.publish.entity.Payload;
import com.spread.it.mqtt.publish.mapper.PayloadMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@ConfigurationProperties(prefix = "payloads", ignoreInvalidFields = true)
public class PayloadConfiguration {
    @Autowired
    private PayloadMapper payloadMapper;

    public List<Payload> payloads;

    public List<Payload> getPayloads() {
        return payloads;
    }

    public void setPayloads(List<Payload> payloads) {
        this.payloads = payloads;
    }

    /**
     *
     */
    public PayloadConfiguration() {
        //初始化數據到數據表中
        this.init();
    }

    /**
     * 進行數據的初始化
     */
    public void  init(){

    }
}
