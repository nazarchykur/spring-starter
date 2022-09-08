package com.study.spring.database.repository;

import com.study.spring.database.pool.ConnectionPool;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

//по замовчуванню всі біни  singleton

//@Scope("singleton")
//@Scope("prototype")
//@Scope(BeanDefinition.SCOPE_SINGLETON)
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
@Repository
@RequiredArgsConstructor
public class UserRepository {
    @Qualifier("pool2")
    private final ConnectionPool connectionPool;

    
    /*
            nested exception is org.springframework.beans.factory.NoUniqueBeanDefinitionException: 
            No qualifying bean of type 'com.study.spring.database.pool.ConnectionPool' available: 
                expected single matching bean but found 2: pool1,pool2
                
                так як тепер спрінг знайшов 2 біна, то ми уточнюємо через    @Qualifier("pool2") , що це саме    pool2
                
                у конфігурації
                        @Bean
                        public ConnectionPool pool2() {
                            return new ConnectionPool("test-name", 20);
                        }
     */
}
