package com.spread.it.mqtt.publish.service;

import com.alibaba.fastjson.JSON;
import com.spread.it.mqtt.publish.entity.Payload;
import com.spread.it.mqtt.publish.mapper.PayloadMapper;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.mqtt.outbound.MqttPahoMessageHandler;
import org.springframework.stereotype.Service;
import org.springframework.ui.ConcurrentModel;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;

import java.io.UnsupportedEncodingException;

@Service
public class MQTTServiceImpl {
    //    public static final String HOST = "tcp://192.168.2.50:1883";
    public static final String HOST = "tcp://localhost:1883";
    public static final String TOPIC_SUBACTION = "kbeacon/subaction/D03304001422";
    public static final String TOPIC_PUBLISH = "kbeacon/publish/D03304001422";
    public static final String TOPIC_PUBACTION = "kbeacon/publish/D03304001422";
    public static final String TOPIC_LOCAL = "someQueue";
    private static final String CLIENT_ID = "MQTT_SERVER";
    private MqttClient client;
    private MqttTopic topicSubaction;
    private MqttTopic topicPublish;
    private MqttTopic topicPubaction;
    private MqttTopic localTopic;
    private String userName = "admin";
    private String passWord = "password";
    @Autowired
    private PayloadMapper payloadMapper;


    private static final String jsonStr = "{\"msg\":\"dData\",\"mac\":\"7573010A33DD\",\"seq\":14,\"auth1\":\"00000000\",\"dType\":\"ascii\",\"data\":\"020000000C128014FF00037FFEF9F33FCFEF7EFCE7E0DF070FF01FF8zGzGzGzGzGzGzGzGzGyGXNmGOUmGUPmGUPmGPOmGXNzGrGE2YmGD8OmGDDPmGDDPmGD8OmGE2YrG80HJlG80HYgGDEYbGaS8FgGDCUbGOMOgGDDPbG9FLSgGDDPbG9FLSgGDDPbG9FLSgGC1PbG9FLSlGOF1SlGC7E3C7gGE1PbGTVOgGCCPbGZYhGDEPmGU6FmGUAFmGPObG80NlGRHWlGZJC3lGF1GMbGHNgGMGLaGRHVcGC0WaGMGLaGXHIcGPbGMGLaGTGE1cGSbGMGLaGE3GZcGF7bGLGMaGC7GRJfGZJC3aGOGKJbGFBbGKHVaGOGKJbGBBVbG80NaGOGKJbGBB6FfGOGKJbGBB6FfGC7GRJbGC06FfGE3GZcGDB6FbG80NaGXJTcGDB6FaGRHWaGZH01cGDBVaGZJC3aGRHVcGFBUaGF1GMbG80YcGaPaGMGLhGD8WaGMGLhGBBF7aGMGLbGKVcGFBbGMGLaGSZIdGOaGLGMaGOX01dGF7aGZJC3aGOF1XcGT37aGKHVaGOE3RJbGEDB7bG80NaGOSKJbGEDB7fGOSKJbGEDB7fGOSKJbG80VcGLaGaSKJbGEDBFaGKWLaGE3SRJbGEDBFaGZVLaGF1MRcGEDBFaGLE3LaGZ79XcGEDBFaGF1aLaGRH01cGTYaGMZLaGKHIgGMRLbGC0YcGFBbGMK79hG9BbGMK39hGA8VaGF1G19hGB557aGLG81hGA957aGRGC1hG9957cGE1eGWaGA557iGKVaGA557cGFDdGRIaGA157bGLZdGRIaG0CVaGK79LdGRIaGSbGK79MdGRIaGEBbGK79SdGRIeGK79OdGKVaGPF7aGK799FeGWaGPF7aGK78NhGEDVaGTaHNgGEB77aGTaHNgGAB77aGK78NhGOVaGK79YcGRIbGF777aGK79OcGXHbGFB77aGK79SaGTNC0HNaG81VaGK79MaGTJ80HYbG77cGF1aGC0JaHWbG77aGRH08aGC0GaHWaGC1VaGRH0DaGC0KaHVbGF7aGRGOaGC0KHZVeGRGOaGC0KIKIeGRGOaGC0KVKIeGRHIaGC0KVGIeGRHIaGC0QVGIjGTQVGIjGTNVGIjGTYIKIjGXW83RVjGXIC0ZVeGXaHJGZbHVeGXaHJGRbHWeGMGLaGKbHYeGMGLbGbHNeGMGLbGC0aHJaGaHIGMGLbGXH01bGaHIGMGLcGHYbGaHIGMGLlGMGLlGMGLhGaHIGMGLlGMGLlGMGLhGaHIGMGLhGaHIGMGLhGaHIGMGLhGaHIGMGLlGXaHJkGXaHJzGeGC0bHIjGC0bHIaGaHIeGC0bHIaGaHIeGC0bHIaGaHIeGC0bHIfGHbGTbHIfGH01aGXbHIaGaHIaGN80aGXYdGaHIaGNRJGZWdGaHIaGNKJGZWdGaHIaGNKJGRViGNKJGRViGNKJGKVdGaHIaGNKJGKIdGaHIaGNKJGKIdGaHIaGNiGaHIGT01iGaHIGTHYhGaHIaG3CWmGNC3hGaHIaGNF1hGaHIaGNZmGNRJgGaHIaGNKJgGaHIaGNKNgGaHIcGJgGaHIpGLGF1lGLGTJkGLGKJgGaHIGL803EJgGaHIGL803EJgGaHIGL9EQJgGaHIGL9EQJgGaHIGL9EQJkGL9EHJkGL9EHlGL9EJhGaHIGT1EJlGT1EJhGaHIGL9EJhGaHIGL9EWhGaHIGL9EIhGaHIGL9E71hGaHIGL9E78hGaHIGL9E7ChGaHIGL803EJkGL803EJgGaHIGLaGNgGaHIGLaGNkGLaGJgGaHIlGaHIGMjGaHIGMjGaHIGMX01hGaHIGMXHlGaMRJkGaMKJgGaHIGaMKJgGaHIGaMKJgGaHIGaMKJkGaMKJgGaHIGX73KJgGaHIGXIKJgGaHIGMC3mGMnGMGOhGaHIbGOhGaHIGLGShGaHIGLGShGaHIGLGShGaHIGZHIhGaHIGZHIeGUPdGLGMeGDDPdGLGMeG5DPdGLGLeG9DPGaHIGLGLeG9DPGaHIiGDDPGaHIGTaHNGC0YaGDDPGaHIGTaHNGEEOaG9DPdGSGQJGU6FaG5DPGaHIGSP3EJGU6FaGDDPdGS6E4EJGCE5FFDGUPdGE16CC2JGTG9CWfGE469F2JbG8AUJaGaHIGE6603AJbGA2U60WGaHIGS641AJG81GB6U6EUGaHIGS6DDAJKQG0AU6EUGaHIGE36DDAJKGJ9AU6EUGaHIGT6DDAJKGJECWHUdGE405FAJGQJD9G6FUdGS01FAJG81GD7G6FUdGE66802JbGHWIUGaHIGE46C06JbGD3G6CUGaHIGE16CKJG81GDCG6F5FdGE36EQJKQGFBG60WGaHIGSP3EJKGJFBGJaGaHIGSGQJKGJF6WF7eGTaHNGQJE6U77eGTaHNG81GD6U70WkGB6UB7aGaHIgG76UB7aGaHIGKJbGWJB6U80JGaHIGKJaGK67JD6UF79FGaHIGTaHNKF7JE6U95AFGaHIGTaHNKFBJF6W25JGaHIGKaQJKFDJF7G30WdGKaQJGQJFBGA5PdGKaQJdG956FdGKaQJdGB5BFdGKaQJkGKaQJgGaHIGKaQJgGaHIGTaHNgGaHIGTaHNkGKJiGaHIGK4FiGaHIGSOHNbGM9FbGaHIGSOHNGCEJM9FbGaHIGSO3EJGCEJaGM9FGaHIGTH3EJdGM9FdGXH3EJcGPfGMO3EJGF7GMObGaHIGMO3EJK70N14YGYdGMOHNK2BJ47NC0PdGMOHNK8BJQYDEPdGMOaGKDBJ71OCEPGaHIGKJaGR2BJH6FH2FGaHIGKJaGK6BaJYaPGaHIGTaHNGB0NDDAFaPGaHIGTaHNG67G6DBF80WGaHIGKaQJG5FGA1BFF7eGKaQJRHNC9BFPJdGKaQJG4FbGHNdGKaQJG73bGPBFGaHIGKaQJGKNBDJPUGaHIGKaQJRIBFB5JDBaGaHIGKaQJFDGBFA5JDBeGTaHNFDZN14WDBeGTaHNFD01GB56FCBaGaHIGKJaGFDRG816FD3eGK4FaGRIJB55F9BeGSOHNGEEBFA5Y5BeGSOHNG6DG15ND3aGaHIGSO3EJG6BG35JCBaGaHIGTH3EJR01GD5JDBaGaHIGXH3EJG6CJEDGD8YGaHIGMO3EJG6FBFaGUOdGMO3EJkGMOHNkGMOHNkGMOiGaHIzGqGaHIlGaHIlGaHIzGdG\"}";

    public MQTTServiceImpl() throws MqttException {
        client = new MqttClient(HOST, CLIENT_ID, new MemoryPersistence());
        //建立鏈接
        connect();

    }

    /**
     * 創建鏈接并GET topic
     */
    private void connect() {
        MqttConnectOptions options = options();
        client.setCallback(new MqttPahoMessageHandler(HOST, CLIENT_ID));
        try {
            client.connect(options);
            topicPublish = client.getTopic(TOPIC_PUBACTION);
            topicPubaction = client.getTopic(TOPIC_PUBLISH);
            topicSubaction = client.getTopic(TOPIC_SUBACTION);
            localTopic = client.getTopic(TOPIC_LOCAL);

        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    /**
     * 初始化鏈接參數信息
     *
     * @return
     */
    public MqttConnectOptions options() {
        MqttConnectOptions options = new MqttConnectOptions();
        options.setCleanSession(false);
        options.setPassword(userName.toCharArray());
        options.setUserName(passWord);
        options.setConnectionTimeout(10);
        options.setKeepAliveInterval(20);
        return options;
    }

    /**
     * 發佈信息
     *
     * @param topic
     * @param message
     * @throws MqttException
     */
    public Model publish(MqttTopic topic, MqttMessage message) {
        Model model = new ConcurrentModel();
        try {
            MqttDeliveryToken token = topic.publish(message);
            token.waitForCompletion();

            model.addAttribute("message", "message is published completely! " + token.isComplete());
            return model;
        } catch (MqttException e) {
            model.addAttribute("error", e.getMessage());
            return model;
        }
    }

    /**
     * 根據摻入的topic 進行篩選topic
     *
     * @param topicName
     * @return
     * @throws UnsupportedEncodingException
     */
    public Model branch(String topicName) throws UnsupportedEncodingException {
        if (StringUtils.isEmpty(topicName)) {
            throw new IllegalArgumentException("參數信息異常");
        }
        MqttMessage message = new MqttMessage();
        message.setQos(2);
        message.setRetained(true);
        Payload payload = this.payloadMapper.selectOne(null);
        String jsonString = JSON.toJSONString(payload);
        message.setPayload(jsonString.getBytes("UTF-8"));
        if ("subaction".equalsIgnoreCase(topicName)) {
            publish(topicSubaction, message);
        } else if ("publish".equalsIgnoreCase(topicName)) {
            publish(topicPublish, message);
        } else if ("pubaction".equalsIgnoreCase(topicName)) {
            publish(topicPubaction, message);
        }else if ("localTopic".equalsIgnoreCase(topicName)){
            publish(localTopic,message);
        }
        Model model = new ConcurrentModel();
        model.addAttribute("isRetained", message.isRetained());
        model.addAttribute("id", message.getId());
        model.addAttribute("payload", message.getPayload());
        model.addAttribute("qos", message.getQos());
        model.addAttribute("dup", message.isDuplicate());
        return model;
    }
}
