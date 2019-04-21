package com.spread.it.mqttsubscribe.config;

import com.spread.it.mqttsubscribe.component.WebSocketServer;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;

public class MqttPushCallback implements MqttCallback {
    private static final Logger LOGGER = LoggerFactory.getLogger(MqttPushCallback.class);

    @Override
    public void connectionLost(Throwable throwable) {
        LOGGER.debug(Marker.ANY_MARKER, "鏈接發生異常: *", throwable.getMessage());
        System.out.println(String.format("鏈接發生異常: %s", throwable.getMessage()));
    }

    @Override
    public void messageArrived(String topic, MqttMessage mqttMessage) throws Exception {
        LOGGER.debug(Marker.ANY_MARKER, "當前收到來自 * 主題的信息: *", topic, mqttMessage.toString());
        System.out.println(String.format("當前收到來自 %s 主題的信息: %s", topic, mqttMessage.toString()));

        //调用webSocket的群发方法体进行数据推送
        WebSocketServer.sendInfo(new String(mqttMessage.getPayload()));
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
        LOGGER.debug(Marker.ANY_MARKER, "交货完成: *", iMqttDeliveryToken.toString());
        System.out.println(String.format("交貨完成: %s", iMqttDeliveryToken.toString()));
    }
}
