package com.spread.it.mqtt.publish.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 *
 * 消息實體
 * @version 1.0
 * @author lsy
 */
@TableName("MQTT_PAYLOAD")
@Data
public class Payload implements Serializable {
//    private static final String jsonStr = "{\"msg\":\"dData\",\"mac\":\"7573010A33DD\",\"seq\":14,\"auth1\":\"00000000\",\"dType\":\"ascii\",\"data\":\"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\"}";
    @TableId
    private Long id;
    private String msg;
    private String mac;
    private Integer seq;
    private String auth1;
    @TableField("dType")
    private String dType;
    private String data;

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
//    public Integer getSeq() {
//        return seq;
//    }
//
//    public void setSeq(Integer seq) {
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

//    @Override
//    public String toString() {
//        return "Payload{" +
//                "msg='" + msg + '\'' +
//                ", mac='" + mac + '\'' +
//                ", seq=" + seq +
//                ", auth1='" + auth1 + '\'' +
//                ", dType='" + dType + '\'' +
//                ", data='" + data + '\'' +
//                '}';
//    }

}
