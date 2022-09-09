package com.study.spring.database.pool;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Slf4j
@Component("pool1")
@RequiredArgsConstructor
public class ConnectionPool {
    @Value("${db.username}")
    private final String username;
    @Value("${db.pool.size}")
    private final Integer poolSize;

    /*
         так як ми добавили до полів   final  , то ми хочемо щоб обєкт був  immutable
         отже видялаємо сетери  і залишаємо тільки один конструктор , який їх ініціалізує
         а так як він тільки один то він і заавтовайреться, ніби на ньому є анотація  @Autowired
     */

    @PostConstruct
    private void init() {
        log.warn("Init connection pool");
    }

    //    методи destroy викликаються тільки тоді коли закривається ApplicationContext
    @PreDestroy
    private void destroy() {
        log.info("Clean connection pool");
    }
}
