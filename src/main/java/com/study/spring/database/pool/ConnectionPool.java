package com.study.spring.database.pool;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Component("pool1")
public class ConnectionPool {
    //public class ConnectionPool {
    private final String username;
    private final Integer poolSize;

    public ConnectionPool(@Value("${db.username}") String username,
                          @Value("${db.pool.size}")  Integer poolSize) {
        this.username = username;
        this.poolSize = poolSize;
    }

    /*
         так як ми добавили до полів   final  , то ми хочемо щоб обєкт був  immutable
         отже видялаємо сетери  і залишаємо тільки один конструктор , який їх ініціалізує
         а так як він тільки один то він і заавтовайреться, ніби на ньому є анотація  @Autowired
     */

    @PostConstruct
    private void init() {
        System.out.println("Init connection pool");
    }

    //    методи destroy викликаються тільки тоді коли закривається ApplicationContext
    @PreDestroy
    private void destroy() {
        System.out.println("Clean connection pool");
    }
}
