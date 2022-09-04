import com.study.spring.database.pool.ConnectionPool;
import com.study.spring.database.repository.CompanyRepository;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class AppRunner {
    public static void main(String[] args) {
    /*
    lesson_3.2_Annotation-based_Configuration_BeanPostProcessor

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

        отже є BeanPostProcessors які дивляться за відповідними своїми анотаціями і знають як їх опрацьовувати    
        ми їх всіх добавили додавши до xml
                <context:annotation-config/>    
                
                
                BeanPostProcessor 
                        default Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
                            return bean;
                        }
                        
                        default Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
                            return bean;
                        }
                
                
          згідно схеми бачимо, що метод   BeanPostProcessor # postProcessBeforeInitialization()
            викликається до виклику наших    Initialization callbacks
            
            а   метод   BeanPostProcessor # postProcessAfterInitialization()
            зразу після   виклику наших    Initialization callbacks
                
                
         BeanPostProcessor  потрібні для того щоб ще додатково опрацюваті потрібні біни, або ті чи інші анотації 
         
         також ми можемо керувати порядком PostProcessors  за допомогою @Order і @PriorityOrder  анотацій
               як і у випадку з BeanFactoryPostProcessors  так і з  BeanPostProcessors 
               
               
         зі схеми бачимо що Bean Definition  попадають до IoC container, де першим іде        
             1)   BeanFactoryPostProcessors
                        за допомогою яких ми можемо "підкрути" наші Bean Definition, наприклад заінджектити наші проперті з файлів
                        
            2) далі відсортовуємо  Bean Definition   , щоб мати змогу побачити і додати всі потрібні залежності між бінами   
            
            3) далі через цикл по одному береться      Bean Definition 
                   - 3.1) 1 етап ініціалізації, тобто викликається відповідний конструктор
                   - 3.2) далі ідуть сетери
                   
                   - 3.3)  далі ідуть пост процесори
                                BeanPostProcessor # postProcessBeforeInitialization(Object bean, String beanName)
                                        
                                        так як ми вже викликали конструктор і сетери, то на цьому етапі  передається вже готовий БІН і його ID
                
                    - 3.4) опісля викликається   Initialization callbacks
                            один із 3 способів, хоча на практиці це буде перший = @PostConstruct
                
                    - 3.3)  далі ідуть пост процесори
                            BeanPostProcessor # postProcessAfterInitialization(Object bean, String beanName)
                            
                            так як тут вже пройшли всі стадії щодо ініціалізації біна, то на цій стадії 
                            зазвичай створюється ПРОКСІ, підміна бінів, і.т.д
                
                     - 4) на цьому етапі ми вже маємо повністю готовий БІН  
                                якщо це СІНГЛТОН , то він залишається у КОНТЕЙНЕРІ
                                
                     - 5) Destruction callbacks
                            один із 3 способів, хоча на практиці це буде перший = @PreDestroy
                            коли закривається КОНТЕКСТ
                
                
                
                
                
                
         
         
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
