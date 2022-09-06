package com.study.spring.database.repository;

import com.study.spring.database.pool.ConnectionPool;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

//по замовчуванню всі біни  singleton

//@Scope("singleton")
//@Scope("prototype")
//@Scope(BeanDefinition.SCOPE_SINGLETON)
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
@Repository
public class UserRepository {

    private final ConnectionPool connectionPool;

    public UserRepository(ConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }
}
