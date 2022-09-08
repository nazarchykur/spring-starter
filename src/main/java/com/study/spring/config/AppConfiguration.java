package com.study.spring.config;

import com.study.spring.database.pool.ConnectionPool;
import com.study.spring.database.repository.UserRepository;
import com.web.config.WebConfiguration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile; 


@Import(WebConfiguration.class)
@Configuration
public class AppConfiguration {


//        1 спосіб   щодо назви біна

//    @Bean
//    public ConnectionPool pool2() {
//        return new ConnectionPool("test-name", 20);
//    }

//        2 спосіб   щодо назви біна
//    @Bean("pool2")
//    public ConnectionPool connectionPool() {
//        return new ConnectionPool("test-name", 20);
//    }


//    можна додати   Scope

//    @Bean("pool2")
//    @Scope(BeanDefinition.SCOPE_PROTOTYPE)
//    @Scope(BeanDefinition.SCOPE_SINGLETON)
//    public ConnectionPool connectionPool() {
//        return new ConnectionPool("test-name", 20);
//    }


    //    також можна заінжектити значення через    @Value("${db.username}")
    @Bean("pool2")
    public ConnectionPool pool2(@Value("${db.username}") String username) {
        return new ConnectionPool(username, 20);
    }


//  можна передати потрібний бін  через параметр
//            до параметру також можна застосовувати     @Qualifier("pool2"), якщо потрібно уточнити який саме бін
//            в даному випадку ми зразу вказали імя  pool2 

    /*
        @Profile("prod")
        часто з цією анотацією можна зустріти символи   ! & | 
        що відповідно означають  Не І Або
        
        @Profile("!prod")     - всі інші тільки не prod
        @Profile("dev|qa")    - створювати даний бін тоді коли або dev або qa
        @Profile("prod&web")  - тільки тоді коли прод + веб
        
    
     */
    @Bean
    @Profile("prod")
    public UserRepository userRepository2(ConnectionPool pool2) {
        return new UserRepository(pool2);
    }

    @Bean
    public ConnectionPool pool3() {
        return new ConnectionPool("test-pool", 20);
    }

    @Bean
    public UserRepository userRepository3() {
        ConnectionPool connectionPool1 = pool3();
        ConnectionPool connectionPool2 = pool3();
        ConnectionPool connectionPool3 = pool3();
        return new UserRepository(pool3());
    }
}
