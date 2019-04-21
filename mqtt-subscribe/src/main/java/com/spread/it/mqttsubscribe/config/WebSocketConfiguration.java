package com.spread.it.mqttsubscribe.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * @author lsy
 * @version 1.0.0
 * @apiNote 開啓對webSocket的支持
 */
@Configuration
public class WebSocketConfiguration {
    /**
     * 服务器端点导出器
     */
    @Bean
    public ServerEndpointExporter serverEndpointExporter(){
        return new ServerEndpointExporter();
    }

}
