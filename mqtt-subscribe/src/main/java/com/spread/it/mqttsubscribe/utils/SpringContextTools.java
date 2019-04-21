package com.spread.it.mqttsubscribe.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class SpringContextTools implements ApplicationContextAware {

    private static ApplicationContext applicationContext;
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext=applicationContext;
        System.out.println("context:"+this.applicationContext);
    }

    public static ApplicationContext getApplicationContext(){
        return applicationContext;
    }

    public static Object getBean(Class tClass){
        Object bean = applicationContext.getBean(tClass);
        return bean;
    }
    public static Object getBeanByName(String name,Class tClass){
        Object bean = applicationContext.getBean(name, tClass);
        return bean;
    }

    public static Object getBeanByName(String name){
        Object bean = applicationContext.getBean(name);
        return bean;
    }
}
