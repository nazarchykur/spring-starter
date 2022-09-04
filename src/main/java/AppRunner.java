import com.study.spring.database.pool.ConnectionPool;
import com.study.spring.database.repository.CompanyRepository;
import com.study.spring.database.repository.UserRepository;
import com.study.spring.ioc.Container;
import com.study.spring.service.UserService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class AppRunner {
    public static void main(String[] args) {
        
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("application.xml");
        ConnectionPool connectionPool = context.getBean("pool1", ConnectionPool.class);
        System.out.println(connectionPool);  
        
//        з використанням фабричного методу
        CompanyRepository companyRepository = context.getBean("companyRepository", CompanyRepository.class);
        System.out.println(companyRepository);
        
        /*
            IMEGES
                resources/images/lesson_7_xml-based_factory-method-injection/factory-method-injection.png
                
                
             у дебаг режимі перевіряємо  companyRepository  (так як всі біни по замовчуванню  SINGLETON , 
                    то можемо бачити одинакоий код -> наприклад ConnectionPool@1676)
                            
         */
        

    }
}
