import com.study.spring.config.AppConfiguration;
import com.study.spring.database.pool.ConnectionPool;
import com.study.spring.database.repository.CompanyRepository;
import com.study.spring.database.repository.CrudRepository;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class AppRunner {
    
    /*
     
        @Configuration
        так, як ці анотації йдуть від    BeanFactoryPostProcessor, то вони будуть опрацьовуватися у першу чергу (див перші лекції)
        
        флоу +- такий:
        
        зазвичай створюється окрема папка  наприклад   config  , де створюється всі потрібні конфігурації
            - конфігурації щодо БД
            - для роботи з іншими сервісами
            - для роботи з веб додатками
            - для роботи, наприклад з якимось Messages Broker 
            ...
            
        @Bean
            анотація @Bean , яка застосовується до методу, щоб вказати, що він повертає bean, яким керує контекст Spring. 
            @Bean зазвичай оголошується в методах класів конфігурації  @Configuration.
            
            1)
            method name => is the bean id/bean name
            
            2) можемо надати інший ідентифікатор Bean
                @Bean(name = "someOtherName") 
                
            3) Ще одна цікава річ: ми можемо дати кілька імен цьому методу 
                @Bean(name = {"name1", "name2"}) 
                
          -------------------------------------------------------------------------------------------------------------      
          
          ще раз узагальнимо:
          
            Зазвичай, коли ми створюємо bean, ми визначаємо його за допомогою інтерфейсу, причому bean є його конкретною реалізацією. 
            Це забезпечує перевагу можливості заміни реалізацій, просто змінивши файл конфігурації 
            (припускаючи, що інші реалізації вже створено). 
            Якщо bean був визначений реалізацією, а не інтерфейсом, коли ви вирішите змінити реалізацію на іншу, 
            вам потрібно буде знайти всі його використання (які можуть існувати в багатьох окремих файлах).
            
                        @Configuration
                        public class AppConfig {
                        
                            @Bean
                            public MyBean myBean() {
                                return new MyBeanImpl();
                            }
                        
                            @Bean({"myOtherBean", "beanNameTwo"})
                            public MyBean myOtherBeanWithDifferentName() {
                                return new MyOtherBeanImpl();
                            }
                        
                        }
                        
                        
          Тут у нас є @Configuration клас, який ми можемо використовувати для створення та використання компонентів у контексті програми. 
          
          @Bean використовується для позначення методу як такого, що створює bean, і Spring додасть його до контексту. 
          Повернутий тип методу визначає тип створюваного bean-компонента, тому обидва bean-компоненти, створені в цьому прикладі, 
          будуть посилатися на тип MyBean, а не на їхні реалізації. 
          Ім'я методу визначатиме ім'я створеного bean-компонента або ім'я/імена можна передати в @Bean анотацію. 
          Якщо це зроблено за допомогою анотації, просто додайте ім’я bean-компонента або використовуйте масив, 
          щоб надати кілька псевдонімів bean-компоненту.
          
          MyBeanЩе одна річ, на яку слід звернути увагу, це те, що інтерфейс використовують два компоненти . 
          Через це, коли ці боби вводяться, їх потрібно називати правильними назвами, щоб їх можна було правильно розрізнити 
          
          
           Ще варто згадати, @Component анотації на MyBeanImpl.
           Це означає що це бін він автоматично знайдеться (auto-detected) Spring-ом , і впринципі 
           що дозволить виключити її з конфігураційних файлів. 
           Отже, якби ми хотіли, ми могли б просто видалити файл конфігурації, який було визначено вище, 
           і програма все одно працюватиме правильно. Хоча, щоб це працювало таким же чином, нам потрібно буде надати 
           імена компонентам, інакше компонент буде названо за класом, до якого розміщено анотацію. 
           Таким чином, якщо @Component додано до MyBeanImpl створеного bean-компонента, він буде називатися «myBeanImpl», 
           тоді як якщо він буде анотований, @Component("myBean")він буде називатися «myBean».
                        
                                @Component
                                public class MyBeanImpl implements MyBean {
                                
                                  @Override
                                  public void someMethod() {
                                    System.out.println(getClass() + ".someMethod()");
                                  }
                                }
                                
                                
          зазвичай у класі конфігурації будуть кілька бінів які якось взаємозалежні, тобто один бін буде як параметр для іншого
          
                    @Configuration
                    public class AppWithInjectionConfig {
                    
                          @Autowired private MyBean myOtherBean;
                        
                          @Bean
                          public MyBean myBeanWithInjection(final MyBean myBean) {
                            return new MyBeanWithInjectionImpl(myBean);
                          }
                        
                          @Bean
                          public MyBean myOtherBeanWithInjection(@Qualifier("myOtherBean") final MyBean myBean) {
                            return new MyBeanWithInjectionImpl(myBean);
                          }
                        
                          @Bean
                          public MyBean myBeanWithAutowiredDependency() {
                            return new MyBeanWithInjectionImpl(myOtherBean);
                          }
                        
                          @Bean
                          public MyBean myBeanWithMethodInjectedDependency() {
                            return new MyBeanWithInjectionImpl(myBeanWithAutowiredDependency());
                          }
                    } 
                    
                    
            Тут ми розглянемо, як створити bean-компоненти, які вимагають впровадження залежностей. 
            Якщо ви подивитеся на перший метод myBeanWithInjection, він приймає myBean як один із своїх параметрів і 
            передає його в конструктор MyBeanWithInjection. 
            
            Якщо вказати myBeanв параметрі методу, Spring шукатиме bean відповідного типу, якщо існує лише один, 
            або за ідентифікатором, якщо є кілька версій. Отриманий bean потім вставляється в bean, який створюється. 
            
            Наступні методи в цьому прикладі демонструють подібні способи впровадження залежностей, оскільки всі вони 
            використовують ін’єкцію конструктора, але отримують ін’єктований bean різними способами. 
            
            myOtherBeanWithInjection використовує @Qualifier щоб вказати ім’я bean-компонента, який слід отримати з контексту, 
            дозволяючи параметру мати іншу назву, що може бути корисним, якщо ім’я bean-компонента дуже довге, 
            і ви не хочете писати його кілька разів і myBeanWithAutowiredDependency використовуєте @Autowired анотація 
            для отримання myOtherBean з контексту. 
            
            Інший спосіб введення bean-компонента — це виклик іншого методу, який має анотацію @Bean зсередини 
            конструктора або методу bean-компонента, який ви хочете створити. 
            
            Ви вирішуєте, чи віддаєте перевагу цьому способу отримання bean-компонента чи ні, але він має обмеження 
            на роботу лише з bean-компонентами, які були визначені в тому самому файлі конфігурації.

            
            
            Існує також інший спосіб, який слід згадати, а не введення залежностей через конструктори, залежності можуть 
            бути введені в bean безпосередньо за допомогою @Autowired анотації до властивості, яку потрібно отримати. 
            Це виглядатиме так, як показано нижче.
            
                        @Component
                        public class MyBeanWithInjectionImpl implements MyBean {
                        
                            @Autowired
                            private MyBean myBean;
                        
                            @Override
                            public void someMethod() {
                                System.out.print("from injection: ");
                                myBean.someMethod();
                            }
                        }
                           
           Недолік використання@Autowiredпорівняно з ін’єкцією конструктора полягає в тому, що він приховує конфігурацію 
           від розробника через те, що залучає залежності, які згадуються лише всередині його класу, що ускладнює для 
           нас побачити, які залежності використовуються в компонентах під час перегляду файлів конфігурації. 
           При використанні ін’єкції конструктора зрозуміло, що потрібно, і bean не може бути створений, доки все 
           необхідне не буде отримано та передано в конструктор.
           
           в обох способів є свої плюси і мінуси
           
           
           
           
           
           
           
           @Configuration
            public class AppWithPropertyInjectionConfig {
            
                @Value("${propertyOne}")
                private String propertyOne;
            
                @Value("${propertyTwo}")
                private String propertyTwo;
            
                @Bean
                public MyBean myBeanWithProperties() {
                    return new MyBeanWithPropertiesImpl(propertyOne, propertyTwo);
                }
            
                @Bean
                public MyBean myOtherBeanWithProperties(
                                @Value("${propertyOne}") final String propertyOne,
                                @Value("${propertyTwo}") final String propertyTwo) {
                    return new MyBeanWithPropertiesImpl(propertyOne, propertyTwo);
                }
            
                @Bean
                public MyBean myBeanWithMethodInjectedProperties() {
                    return new MyBeanWithInjectionImpl(myOtherBeanWithProperties(null, null));
                }
            }
            
          Перше, на що ми повинні звернути увагу, це @Valueанотація, яка читає значення з файлу властивостей. 
          Наразі ці значення зчитуються з application.properties.                    
     */
    public static void main(String[] args) {
        /*
        так як ми переходимо на використання тільки анотації, то замінюємо відповідний контекст клас 
        замість   ClassPathXmlApplicationContext context =  new ClassPathXmlApplicationContext("application.xml")   де ми вказували xml файл
        вказуємо  AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfiguration.class)  де передаємо наш конфіг клас
                
         */
        try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfiguration.class)) {
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
