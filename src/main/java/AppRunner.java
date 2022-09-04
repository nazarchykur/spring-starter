import com.study.spring.database.pool.ConnectionPool;
import com.study.spring.database.repository.CompanyRepository;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class AppRunner {
    public static void main(String[] args) {
        
        // щоб викликати destroy method
        try (ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("application.xml")) {
            ConnectionPool connectionPool = context.getBean("pool1", ConnectionPool.class);
            System.out.println(connectionPool);

//        з використанням фабричного методу
            CompanyRepository companyRepository = context.getBean("companyRepository", CompanyRepository.class);
            System.out.println(companyRepository);
        }
        
        /*
        
        IMAGES
                resources/images/lesson_2.6_lifecycle-callbacks/lifecycle-callbacks.png
                resources/images/lesson_2.6_lifecycle-callbacks/set breakpoints to see order_constructor->setter->init-method.png
                
         
         
         Initialization callbacks
         
            суть у тому що після того як викликався конструктор для створення потрібного об'єкта,
            викликаються сетери
            а після них можна додатково викликати у наших обєктів якісь методи для подальшої ініціалізації,
            тобто ще щось зробити додатково
                
                є три способи:
                    1 - @PostConstruct
                    2 - afterPropertiesSet() - InitializingBean
                    3 - init-method - xml
                    
               якщо будуть кілька способів , то порядок по списку     
               але краще не використовувати 2, а так як зараз використовуються в основному тільки анотації, то
               залишається найкращий перший варіант
      
      
         Destruction callbacks 
         
            коли хочемо очистити всі необхідні ресурси, які займає наш бін
              сукупність методів які будуть викликатися коли ми закриваємо наш контекст
              
             ми маємо вибрати тільки один варіант з 3
                 є три способи:
                    1 - @PreDestroy
                    2 - destroy() - DisposableBean
                    3 - destroy-method - xml
            
      
             якщо будуть кілька способів , то порядок по списку     
               але краще не використовувати 2, а так як зараз використовуються в основному тільки анотації, то
               залишається найкращий перший варіант
         
         
          * методи destroy викликаються тільки тоді коли закривається ApplicationContext
               а так як він реалізую Autocloseable, то маємо його закрити
               для цього можемо використати try-with-resources
         
          * викликаються тільки для SINGLETON біна, для прототайп потрібно самому для кожного закривати  
         

        IMAGES => resources/images/lesson_2.6_lifecycle-callbacks/set breakpoints to see order_constructor->setter->init-method.png
          set breakpoints to see order of call:   
            constructor -> setter -> init-method
            
        
                
         */


    }
}
