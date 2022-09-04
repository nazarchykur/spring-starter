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
         PROPERTY INJECTION
         
            щоб використовувати ініціалізацію через  сетер  ми маємо забрати модифікатор  final
            в цьому є один із мінусів використання, тобто ми не можемо зробити наш об'єкт IMMUTABLE - НЕЗМІННИЙ
            другий мінус - можна зробити циклічність одного об'єкта на інший
            
                через конструктор можна було би  ініціалізувати один раз всі наші поля, тобто ми зразу знаємо які залежності нам потрібні 
                    тобто конструктор є краший багатопоточності через можливість створити об'єкт IMMUTABLE
                
           а ініціалізувати через сетер можна тоді коли є якась OPTIONAL залежність


            IMEGES
                resources/images/lesson_8_xml-based_property-injection/IoC beans lifecycle.png
                
                
                
                
         Beans lifecycle
             
                отже на вхід у нас є список    Bean Definition
                    IoC container  читає їх щоб відсортувати і подивитися всі їх залежності
                        -> тобто створюються перше ті біни, які без залежностей, 
                           а далі більш складні, щоб мати можливість створити бін з готовими залежностями
                    
                    
                     по циклу пробігаємося по списку
                          і для кожного  Bean Definition  перше викликається конструктор, щоб створити об'єкт, а далі через сетери
                          
                          
                     і на виході маємо всі готові біни з всіма потрібними залежностями     
                            
         */
        

    }
}
