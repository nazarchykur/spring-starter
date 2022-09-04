package com.study.spring.database.pool;

import org.springframework.beans.factory.InitializingBean;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.List;
import java.util.Map;

//краще не використовувати   implements InitializingBean   бо порушуємо принцип IoC
public class ConnectionPool implements InitializingBean {
//public class ConnectionPool {
    private final String username;
    private final Integer poolSize;
    private final List<Object> args;
    
    private Map<String, Object> properties;

    public ConnectionPool(String username, Integer poolSize, List<Object> args, Map<String, Object> properties) {
        this.username = username;
        this.poolSize = poolSize;
        this.args = args;
        this.properties = properties;
    }

    public void setProperties(Map<String, Object> properties) {
        this.properties = properties;
    }
    @PostConstruct
    private void init() {
        System.out.println("Init connection pool");
    }
    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("afterPropertiesSet connection pool");
    }

//    методи destroy викликаються тільки тоді коли закривається ApplicationContext
    @PreDestroy
    private void destroy() {
        System.out.println("Clean connection pool");
    }
}
