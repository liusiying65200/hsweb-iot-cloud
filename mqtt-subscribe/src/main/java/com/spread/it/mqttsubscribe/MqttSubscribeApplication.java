package com.spread.it.mqttsubscribe;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class MqttSubscribeApplication {
    private static final Logger LOGGER= LoggerFactory.getLogger(MqttSubscribeApplication.class);

    public static void main(String[] args) {
        ConfigurableApplicationContext context =new SpringApplicationBuilder(MqttSubscribeApplication.class).run(args);
        //啟動訂閱
        LOGGER.debug("MQTT SUBSCRIBE SERVER Successful startup!");
    }

}
