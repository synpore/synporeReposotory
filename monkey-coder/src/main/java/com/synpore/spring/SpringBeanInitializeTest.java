package com.synpore.spring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class SpringBeanInitializeTest implements DisposableBean, InitializingBean, ApplicationContextAware {

    public static final Logger log = LoggerFactory.getLogger(SpringBeanInitializeTest.class);

    private String name;

    private String type;

    private ApplicationContext applicationContext;

    public SpringBeanInitializeTest() {
    }


    public void destroy() throws Exception {
        log.info("DisposableBean interface method destroy ");
    }

    public void init() {
        log.info("xml config init method");
    }

    public void destory() {
        log.info("xml config destroy method");
    }

    public void afterPropertiesSet() throws Exception {
        log.info("InitializingBean interface method afterPropertiesSet");
    }



    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        log.info("ApplicationContextAware method");
        this.applicationContext = applicationContext;
    }

}
