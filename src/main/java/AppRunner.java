import com.study.spring.database.pool.ConnectionPool;
import com.study.spring.database.repository.CompanyRepository;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class AppRunner {
    public static void main(String[] args) {
/*
    щоб використовувати анотації 
            import javax.annotation.PostConstruct;
            import javax.annotation.PreDestroy;
            
    потрібно добавити ще одну залежність до build.gradle 
            implementation 'jakarta.annotation:jakarta.annotation-api:1.3.5'  
            
            
    а також додати відповідний бін до      resources/application.xml
            <bean class="org.springframework.context.annotation.CommonAnnotationBeanPostProcessor"/>
 */

        try (ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("application.xml")) {
            ConnectionPool connectionPool = context.getBean("pool1", ConnectionPool.class);
            System.out.println(connectionPool);
            
            CompanyRepository companyRepository = context.getBean("companyRepository", CompanyRepository.class);
            System.out.println(companyRepository);
        }
        
        /*
        
        IMAGES
                resources/images/lesson_3.1_Annotation-based_Configuration/context annotation-config schema.png

                
         
         давай розглянемо цю картинку
         
         у центрі 
                    <context:annotation-config/>
                    
                         |                                 |-  ConfigurationClassPostProcessor -- @Configuration                 
                         | - BeanFactoryPostProcessor    - |
                         |                                 |-  EventListenerMethodProcessor   --- @EventListener
                         |
                         |
                         |
                         |                                                                              |- @Autowired
                         |                                    |- AutowiredAnnotationBeanPostProcessor --|
                         |                                    |                                         |- @Value
                         |                                    |
                         |                                    |
                         |                                    |                                         |- @Resource
                         | - BeanPostProcessor -------------- | CommonAnnotationBeanPostProcessor ------|- @PostConstruct
                                                              |                                         |- @PreDestroy
                                                              |                                          
                                                              |
                                                              |                                          |- @PersistenceContext
                                                              |- PersistenceAnnotationBeanPostProcessor -|
                                                                                                         |- @PersistenceUnit                                                                              
                         
         */


    }
}
