package com.synpore.spring;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class PostProcessor implements BeanPostProcessor {


    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if("initializeTest".equals(beanName)){
            log.info("postProcessBeforeInitialization handle bean initializeTest");
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if("initializeTest".equals(beanName)){
            log.info("postProcessAfterInitialization handle bean initializeTest");
        }
        return bean;
    }
}
