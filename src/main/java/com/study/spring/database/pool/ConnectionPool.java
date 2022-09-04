package com.study.spring.database.pool;

import java.util.List;
import java.util.Map;

public class ConnectionPool {
    private final String username;
    private final Integer poolSize;
    private final List<Object> args;


//    private final Map<String, Object> properties;
    
//    щоб використовувати ініціалізацію через  сетер  ми маємо забрати модифікатор  final
//    в цьому є один із мінусів використання, тобто ми не можемо зробити наш об'єкт IMMUTABLE - НЕЗМІННИЙ
//    другий мінус - можна зробити циклічність одного об'єкта на інший
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
}
