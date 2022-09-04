package com.study.spring.database.pool;

import org.springframework.beans.factory.InitializingBean;

import java.util.List;
import java.util.Map;

//краще не використовувати   implements InitializingBean   бо порушуємо принцип IoC
public class ConnectionPool implements InitializingBean {
//public class ConnectionPool {
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

    private void init() {
        System.out.println("Init connection pool");
    }
    
    /*
    краще не використовувати   implements InitializingBean   бо порушуємо принцип IoC

    ми маємо вибрати тільки один варіант з 3
         є три способи:
            1 - @PostConstruct
            2 - afterPropertiesSet() - InitializingBean
            3 - init-method - xml
            
      якщо будуть кілька способів , то порядок по списку
      
      
      не приймають параметрів і тип повернення void
            void init()
            void afterPropertiesSet()
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("afterPropertiesSet connection pool");
    }

//    методи destroy викликаються тільки тоді коли закривається ApplicationContext
    private void destroy() {
        System.out.println("Clean connection pool");
    }
}
