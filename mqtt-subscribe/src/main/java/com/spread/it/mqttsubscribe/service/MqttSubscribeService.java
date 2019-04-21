package com.spread.it.mqttsubscribe.service;

import com.spread.it.mqttsubscribe.config.MqttPushCallback;
import com.spread.it.mqttsubscribe.config.TopicSubscribeMqttMessageListener;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.UnsupportedEncodingException;


/**
 * 訂閱服務類
 */
@Service
public class MqttSubscribeService {
    public static final String HOST = "tcp://192.168.2.50:1883";
    //相关的设备信息
    public static final String TOPIC_PUBLISH = "kbeacon/publish/D03304001422";
    //推送成功之后的反馈信息
    public static final String TOPIC_PUBACTION = "kbeacon/pubaction/D03304001422";

    private static final String CLIENT_ID = "MQTT_SERVER";
    private MqttClient client;
    private MqttTopic topicPublish;
//    private MqttTopic topicPubaction;
    private String userName = "admin";
    private String passWord = "password";

    /**
     * 啟動訂閱
     */
    @PostConstruct
    public void start() throws MqttException {
        //实例化客戶端
        client = new MqttClient(HOST, CLIENT_ID, new MemoryPersistence());
        //實例化topic
        topicPublish = client.getTopic(TOPIC_PUBLISH);
        //建立鏈接
        subscribe();

    }

    /**
     * 創建鏈接并GET topic
     */

    private void subscribe()  {
        MqttConnectOptions options = new MqttConnectOptions();
        options.setCleanSession(true);
        options.setPassword(userName.toCharArray());
        options.setUserName(passWord);
        options.setConnectionTimeout(10);
        options.setKeepAliveInterval(20);
        client.setCallback(new MqttPushCallback());
        client.setManualAcks(false);
        client.setTimeToWait(0);
        try {
            StringBuffer buffer=new StringBuffer();
            buffer.append(CLIENT_ID).append("已於topic為：").append(HOST).append("失去鏈接...");
            options.setWill(topicPublish,buffer.toString().getBytes("UTF-8"),2,true);
            client.connect(options);
            IMqttMessageListener topicSubactionListener=new TopicSubscribeMqttMessageListener();
            //訂閲設備信息
            client.subscribe(TOPIC_PUBLISH,1,topicSubactionListener);
            //訂閲詳細推送狀態反饋信息
            client.subscribe(TOPIC_PUBACTION,1,topicSubactionListener);

        } catch (MqttException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }


    }


}
