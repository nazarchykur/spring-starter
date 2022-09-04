package com.study.spring.database.repository;

import com.study.spring.database.pool.ConnectionPool;

public class CompanyRepository {

    private final ConnectionPool connectionPool;

//    public CompanyRepository(ConnectionPool connectionPool) {
//        this.connectionPool = connectionPool;
//    }
    
    /*
        фабричний метод
    effective java
        про фабричний метод  описано ефективність його використання у книзі  effective java , але 
        на практиці частіше використовується всерівно конструктор
        
                для використання фабричного методу, конструктор у класі зазвичай роблять приватним
                і створюють статичний метод  of
     */

    private CompanyRepository(ConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }

    public static CompanyRepository of(ConnectionPool connectionPool) {
        return new CompanyRepository(connectionPool);
    }
    
}
