import com.study.spring.database.pool.ConnectionPool;
import com.study.spring.database.repository.CompanyRepository;
import com.study.spring.database.repository.CrudRepository;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class AppRunner {
    
    /*
     анотації, пов’язані з впровадженням залежностей, а саме анотації @Resource , @Inject і @Autowired . Ці анотації надають класам декларативний спосіб вирішення залежностей:

            @Autowired 
            ArbitraryClass arbObject;
            
     На відміну від їх прямого створення (імперативний спосіб):
            ArbitraryClass arbObject = new ArbitraryClass();
            
      Дві з трьох анотацій належать до пакета розширення Java: javax.annotation.Resource і javax.inject.Inject. 
      Анотація @Autowired належить до пакета org.springframework.beans.factory.annotation.  
      
      
      
      @Resource
      Анотація @Resource є частиною колекції анотацій JSR-250 і входить до складу Jakarta EE. Ця анотація має наступні шляхи виконання, упорядковані за пріоритетністю:
            1) Match by Name
            2) Match by Type
            3) Match by Qualifier
            
          Ці шляхи виконання застосовні як до сеттера, так і до поля.   
            
            
       @Inject
       Анотація @Inject належить до колекції анотацій JSR-330. Ця анотація має наступні шляхи виконання, упорядковані за пріоритетністю:
            1) Match by Type
            2) Match by Qualifier
            3) Match by Name
            
        Ці шляхи виконання застосовні як до сеттера, так і до поля. Щоб отримати доступ до анотації @Inject , ми повинні оголосити бібліотеку javax.inject як залежність Gradle або Maven.    
        
        
       @Autowired
       Поведінка анотації @Autowired подібна до анотації @Inject . Єдина відмінність полягає в тому, що анотація @Autowired є частиною Spring framework. Ця анотація має ті самі шляхи виконання, що й анотація @Inject , перераховані в порядку пріоритету:
            1) Match by Type
            2) Match by Qualifier
            3) Match by Name
        Ці шляхи виконання застосовні як до сеттера, так і до польового введення.  
        
        щодо  NoUniqueBeanDefinitionException, викликаною кількома компонентами. 
        Spring Framework не знає, яка залежність bean-компонента повинна бути автоматично підключена. 
        Ми можемо вирішити цю проблему, додавши анотацію @Qualifier , де можемо вказати імя або аліаси  @Qualifier("anotherAutowiredFieldDependency"),
        або переіменувати саму назву поля
        
     */
    public static void main(String[] args) {
        try (ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("application.xml")) {
            ConnectionPool connectionPool = context.getBean("pool1", ConnectionPool.class);
            System.out.println(connectionPool);

            CrudRepository companyRepository = context.getBean("companyRepository", CrudRepository.class);
            System.out.println(companyRepository.findById(1));
        }
        
        /*
            отже ми створили кілька своїх анотацій  @InjectBean,  @Transaction, @Auditing,
            але більшість потрібних анотацій вже є від відповідних постПроцесорів:
                - BeanPostProcessor:
                    - AutowiredAnnotationBeanPostProcessor (@Autowired, @Value)
                    - CommonAnnotationBeanPostProcessor (@Resource, @PostConstruct, @PreDestroy)
                    - PersistenceAnnotationBeanPostProcessor (@PersistenceContext, @PersistenceUnit)
               - BeanFactoryPostProcessor:
                    - ConfigurationClassPostProcessor (@Configuration)
                    - EventListenerMethodProcessor (@EventListener)  
                 
            
         */
        
        
        
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
                
                
         BeanPostProcessor  потрібні для того щоб ще додатково опрацювати потрібні біни, або ті чи інші анотації 
         
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
                                            
                                            так як на цій стадії ми все ще будемо звертатися по ідентифікатору до конкретного класу, 
                                            то на цьому місці має бути конкретний клас, а якщо реалізовувати ПРОКСІ тут
                                            тобто у  postProcessBeforeInitialization()  , то мало того що  на наступному етапі
                                            не виконається @PostConstruct, а створиться ПРОКСІ і при виклику 
                                                    context.getBean("companyRepository", CompanyRepository.class);
                                                    тут буде замість  CompanyRepository.class  якийсь ПРОКСІ  jdk.proxy....
                                                     і наш додаток впаде з ерором
                                                

                
                    - 3.4) опісля викликається   Initialization callbacks
                            один із 3 способів, хоча на практиці це буде перший = @PostConstruct
                
                    - 3.3)  далі ідуть пост процесори
                            BeanPostProcessor # postProcessAfterInitialization(Object bean, String beanName)
                            
                            так як тут вже пройшли всі стадії щодо ініціалізації біна, то на цій стадії 
                            зазвичай створюється ПРОКСІ, підміна бінів, і.т.д
                            
                                    ось чому тут можна повернути ПРОКСІ, щоб додати функціональності
                                                        є 2 види проксі:
                                                            - динаміна - Dynamic Proxy через реалізацію через інтерфейс (по замовчуванню є у джаві, і не потрібні додаткові бібліотеки)
                                                            - через наслідування, але тут вже з використанням бібліотек (Cglib, Javaassit, Byte Buddy ...)
                                                            
                
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
