import com.study.spring.database.pool.ConnectionPool;
import com.study.spring.database.repository.CompanyRepository;
import com.study.spring.database.repository.UserRepository;
import com.study.spring.ioc.Container;
import com.study.spring.service.UserService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class AppRunner {
    public static void main(String[] args) {
        
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("application.xml");
        
        /*
            так само тут тепер через те що scope="prototype"
            то контекст знову проганяє через всю цепочку ініціалізації, щоб повернути новий бін
    
            як висновок стає зрозуміло, що біни зі скоупом прототайп не зберігаються у Мапі
                            clazz -> String -> Map<String, Object>
                            
                 бо ключ має біти унікальний           
         */

                                
        ConnectionPool connectionPool = context.getBean("pool1", ConnectionPool.class);
        System.out.println(connectionPool);  
        
//        з використанням фабричного методу
        CompanyRepository companyRepository = context.getBean("companyRepository", CompanyRepository.class);
        System.out.println(companyRepository);
        
        /*
        
        IMAGES
            resources/images/lesson_9_bean-scope/bean-scope_schema-all.png
            resources/images/lesson_9_bean-scope/bean-scope_prototype.png
                
         Bean Scopes
         
         -> Common
                -> singleton
                -> prototape
            
         -> Web
                -> request   => web scopes активно використовує Proxy, щоб можна було інджектити їх у SINGLETONS
                -> session
                -> application
                -> websocket
                
          -> Custom
            
                
         */
        

    }
}
