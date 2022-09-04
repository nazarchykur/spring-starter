import com.study.spring.database.pool.ConnectionPool;
import com.study.spring.database.repository.CompanyRepository;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class AppRunner {
    public static void main(String[] args) {
        
        try (ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("application.xml")) {
            ConnectionPool connectionPool = context.getBean("pool1", ConnectionPool.class);
            System.out.println(connectionPool);
            
            CompanyRepository companyRepository = context.getBean("companyRepository", CompanyRepository.class);
            System.out.println(companyRepository);
        }
        
        /*
        
        IMAGES
                resources/images/lesson_2.7_injection_from_properties_files/properties-yaml files.png
                resources/images/lesson_2.7_injection_from_properties_files/read from props file by key.png
                resources/images/lesson_2.7_injection_from_properties_files/SPeL read beans and its fields.png
                resources/images/lesson_2.7_injection_from_properties_files/read value form props using SPeL.png
                
         
         
         Injection from properties files
         
             Отже ще раз, у СПРІНГУ все крутиться довкола IoC container
             
             properties/yaml  file  це простий текстовий файл, який може мати свою структуру і мати код конвеншн 
             
             ці файли ми маємо надати для конфігурації (різні env, profiles, DB ... інші проперті)
             так як це текстовий файл (не потрібно перекомпільовувати), то ми надаємо його для зчитування спрінгу
             для цього у папці 
                    resources/application.properties
                    
                    
              так як це приклад для xml, то додаємо ще один бін, де вказуємо де розташований наш файл або файли:
                        <bean class="org.springframework.context.support.PropertySourcesPlaceholderConfigurer">
                            <property name="location" value="application.properties"/>
                        </bean>      
                
         */


    }
}
