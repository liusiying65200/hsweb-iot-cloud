//package com.spread.it.mqtt.publish.entity;
//
//import javax.persistence.*;
//
//@Entity
//@Table(name = "MQTT_PAYLOAD")
//public class MqttPayload {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//    private String msg;
//    private String mac;
//    private Long seq;
//    private String auth1;
//    private String dType;
//    private String data;
//
//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public String getMsg() {
//        return msg;
//    }
//
//    public void setMsg(String msg) {
//        this.msg = msg;
//    }
//
//    public String getMac() {
//        return mac;
//    }
//
//    public void setMac(String mac) {
//        this.mac = mac;
//    }
//
//    public Long getSeq() {
//        return seq;
//    }
//
//    public void setSeq(Long seq) {
//        this.seq = seq;
//    }
//
//    public String getAuth1() {
//        return auth1;
//    }
//
//    public void setAuth1(String auth1) {
//        this.auth1 = auth1;
//    }
//
//    public String getdType() {
//        return dType;
//    }
//
//    public void setdType(String dType) {
//        this.dType = dType;
//    }
//
//    public String getData() {
//        return data;
//    }
//
//    public void setData(String data) {
//        this.data = data;
//    }
//
//    @Override
//    public String toString() {
//        final StringBuffer sb = new StringBuffer("com.spread.it.mqttsubscribe.entity.MqttPayload{");
//        sb.append("auth1='").append(auth1).append('\'');
//        sb.append(", data='").append(data).append('\'');
//        sb.append(", dType='").append(dType).append('\'');
//        sb.append(", id=").append(id);
//        sb.append(", mac='").append(mac).append('\'');
//        sb.append(", msg='").append(msg).append('\'');
//        sb.append(", seq=").append(seq);
//        sb.append('}');
//        return sb.toString();
//    }
//}
