//package com.spread.it.mqtt.publish.config;
//
//
//import org.springframework.boot.autoconfigure.domain.EntityScan;
//import org.springframework.context.annotation.Bean;
//import org.springframework.core.Ordered;
//import org.springframework.core.annotation.Order;
//import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
//import org.springframework.orm.jpa.support.PersistenceAnnotationBeanPostProcessor;
//import org.springframework.transaction.annotation.EnableTransactionManagement;
//
//@Order(Ordered.HIGHEST_PRECEDENCE)
//@EnableTransactionManagement(proxyTargetClass = true)
//@EnableJpaRepositories(basePackages = "com.spread.it.mqtt.publish.dao")
//@EntityScan(basePackages = "com.spread.it.mqtt.publish.entity")
//public class JpaConfiguration {
//    @Bean
//    PersistenceAnnotationBeanPostProcessor persistenceAnnotationBeanPostProcessor(){
//        return new PersistenceAnnotationBeanPostProcessor();
//    }
//}
