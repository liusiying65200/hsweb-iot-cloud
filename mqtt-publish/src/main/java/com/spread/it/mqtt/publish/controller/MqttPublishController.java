package com.spread.it.mqtt.publish.controller;

import com.spread.it.mqtt.publish.entity.Payload;
import com.spread.it.mqtt.publish.service.MQTTServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;

@RestController
@RequestMapping("/mqtt")

public class MqttPublishController {
    @Autowired
    private MQTTServiceImpl service;

    @GetMapping("/publish/{topic}")
    public Model publish(@PathVariable String topic) throws UnsupportedEncodingException {
        Model model = service.branch(topic);
        return model;
    }
    @PutMapping("/add")
    public ResponseEntity<Object> addPayload(@RequestBody Payload payload){
        if (payload==null){
            return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("參數信息錯誤");
        }
       Boolean status= this.service.add(payload);
        if (status){
            return ResponseEntity.ok("添加成功");
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("操作失敗");
    }
}
