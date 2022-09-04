import com.study.spring.database.pool.ConnectionPool;
import com.study.spring.database.repository.CompanyRepository;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class AppRunner {
    public static void main(String[] args) {
/*
        оскільки він має бути першим у ції цепочці, то СПРІНГ його має перше найти і поставити у ції цепочці першим
        для цього використовується  проста перевірка спеціального методу
                isAssignableFrom
                
         тобто береться цей клас і перевіряється чи він звязаний з цим класом чи ні 
         
         якщо дуже покопатися у методі getBean(Class clazz)
            то в дефолтній реалізації можна побачити Resolver   forRawClass()
                де всередині через ClassUtils.isAssignable()  йде далі перевірка до методу    isAssignableFrom()
                
         це все для того аби ми дістали потрібні біти конкретного типу, тому такі біни потрібні для ініціалізації інших бінів
         тому то така цепочка є у цьому IoC container     
 */

        String value = "hello";
        System.out.println(CharSequence.class.isAssignableFrom(value.getClass())); // true

        System.out.println(BeanFactoryPostProcessor.class.isAssignableFrom(value.getClass())); // false
        
        

        try (ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("application.xml")) {
            ConnectionPool connectionPool = context.getBean("pool1", ConnectionPool.class);
            System.out.println(connectionPool);
            
            CompanyRepository companyRepository = context.getBean("companyRepository", CompanyRepository.class);
            System.out.println(companyRepository);
        }
        
        /*
        
        IMAGES
                resources/images/lesson_2.8_BeanFactoryPostProcessor/beans lifecycle + BeanFactoryPostProcessor.png

                
         
         
         BeanFactoryPostProcessor
         
                public interface BeanFactoryPostProcessor {
                        void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory)
                }
                
                
         тепер зайдемо на його імплементацію, тобто клас 
                        PropertySourcesPlaceholderConfigurer  postProcessBeanFactory() 
               
              якраз у цьому методі і відбувається ініціалізація проперті сорси і енвайромент 
              де пізніше і підставляються значення з нашого файлу
                        resources/application.properties
                    
                    
                  

         
             Отже повертаємось до beans lifecycle, де до IoC container додаємо тепер BeanFactoryPostProcessor
             
                        images => resources/images/lesson_2.8_BeanFactoryPostProcessor/beans lifecycle + BeanFactoryPostProcessor.png
                    
             тобто тут СПРІНГ створює ці біни (їх може бути кілька), з допомогою якого ми читаємо всі потрібні значення 
             з проперті файлів, а далі та сама цепочка, яку ми переглянули на попередніх уроках
                    
                    
             оскільки  BeanFactoryPostProcessor  має бути першим у ції цепочці, то СПРІНГ його має перше найти і поставити у ції цепочці першим
             для цього використовується  проста перевірка спеціального методу 
                            isAssignableFrom     
                
         */


    }
}
