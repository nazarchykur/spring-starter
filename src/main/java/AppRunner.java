import com.study.spring.database.pool.ConnectionPool;
import com.study.spring.database.repository.CompanyRepository;
import com.study.spring.database.repository.UserRepository;
import com.study.spring.ioc.Container;
import com.study.spring.service.UserService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class AppRunner {
    public static void main(String[] args) {
        /*
        давайте подивимось на конкретну реалізацію нашого IoC container
            для цього додамо дві залежності у build.gradle dependencies:
                        implementation 'org.springframework:spring-core:5.3.22'
                        implementation 'org.springframework:spring-context:5.3.22'
                        
                        
                        
             глянемо на перший варіант BeanFactory
                        public interface BeanFactory   
                    
              де є основний функціонал по отриманню бінів:
                        - containsBean(String name)
                        - getAliases(String name)
                        - getBean(String name)
                        - getType(String name)
                        - isPrototype(String name)
                        - isSingleton(String name)
                        ...
                        
                        
                        
             нас цікавить ApplicationContext
             
                    public interface ApplicationContext extends EnvironmentCapable, ListableBeanFactory, HierarchicalBeanFactory,
		                                                        MessageSource, ApplicationEventPublisher, ResourcePatternResolver   
		                                                        
		     у якому набагато більше функціональності i його основні реалізації:
		            - AnnotationConfigApplicationContext 
		            - AnnotationConfigWebApplicationContext
		            - XmlWebApplicationContext
		            - FileSystemXMLApplicationContext
		            - ClassPathXmlApplicationContext
                         
         */

        
        
        
        
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("application.xml");
//        System.out.println(context.getBean(ConnectionPool.class));  // com.study.spring.database.pool.ConnectionPool@6e06451e

        
        
        
        /*
        
        дивись зображення з ПАПКИ:
                - resources/images/lesson_2-spring-container/
                
        
                можемо запустити це у дебаг режимі і зайти у beanDefinitionMap і побачимо, що там є наш один бін ConnectionPool
                        context -> beanFactory -> beanDefinitionMap -> 
                            key = com.study.spring.database.pool.ConnectionPool#0
                            value = Generic bean: class [com.study.spring.database.pool.ConnectionPool]; scope=; abstract=false; lazyInit=false; autowireMode=0; dependencyCheck=0; autowireCandidate=true; primary=false; factoryBeanName=null; factoryMethodName=null; initMethodName=null; destroyMethodName=null; defined in class path resource [application.xml]
                            
                також нижче є singletonObjects
                де є трохи більше бінів, які спрінг створив, щоб керувати життєвим циклом контейнера:
                    - com.study.spring.database.pool.ConnectionPool#0 -> {ConnectionPool@1660} 
                    - applicationStartup -> {DefaultApplicationStartup@1577} 
                    - systemEnvironment -> {Collections$UnmodifiableMap@1663}  size = 76
                    - lifecycleProcessor -> {DefaultLifecycleProcessor@1574} 
                    - applicationEventMulticaster -> {SimpleApplicationEventMulticaster@1576} 
                    - environment -> {StandardEnvironment@1568} "StandardEnvironment {activeProfiles=[], defaultProfiles=[default], propertySources=[PropertiesPropertySource {name='systemProperties'}, SystemEnvironmentPropertySource {name='systemEnvironment'}]}"
                    - systemProperties -> {Properties@1668}  size = 53   
                    - messageSource -> {DelegatingMessageSource@1575} "Empty MessageSource"       
         */
        
        /*
         так як ми можемо у 
                resources/application.xml
         створити кілька однакових бінів, то у дебаг режимі можна зайти у beanDefinitionMap і побачимо, що там є кілька бінів ConnectionPool
                        context -> beanFactory -> beanDefinitionMap -> 
                            com.study.spring.database.pool.ConnectionPool#1 -> {GenericBeanDefinition@1647} "Generic bean: class [com.study.spring.database.pool.ConnectionPool]; scope=; abstract=false; lazyInit=false; autowireMode=0; dependencyCheck=0; autowireCandidate=true; primary=false; factoryBeanName=null; factoryMethodName=null; initMethodName=null; destroyMethodName=null; defined in class path resource [application.xml]"
                            com.study.spring.database.pool.ConnectionPool#0 -> {GenericBeanDefinition@1648} "Generic bean: class [com.study.spring.database.pool.ConnectionPool]; scope=; abstract=false; lazyInit=false; autowireMode=0; dependencyCheck=0; autowireCandidate=true; primary=false; factoryBeanName=null; factoryMethodName=null; initMethodName=null; destroyMethodName=null; defined in class path resource [application.xml]"    
                
                
                
                            
         так як ми хочемо взяти з контексту по класу бін, а у нас їх зараз 2, то буде ексепшн
         
                ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("application.xml");
                System.out.println(context.getBean(ConnectionPool.class));  // тут буде NoUniqueBeanDefinitionException
                
                Exception in thread "main" org.springframework.beans.factory.NoUniqueBeanDefinitionException: No qualifying bean of type 'com.study.spring.database.pool.ConnectionPool' available: expected single matching bean but found 2: 
                    com.study.spring.database.pool.ConnectionPool#0,
                    com.study.spring.database.pool.ConnectionPool#1
                    
         тому що спрінг не знає який бін потрібно повернути, бо знайшов 2 біни, які підходять, 
         тому що це ж мапа: 
                Map<String, Object> context     
          
         а так як ми передали знайти бін по класу (context.getBean(ConnectionPool.class) ), то ключ 
                clazz -> String ->  Map<String, Object> context 
                
          тому якщо у нас є кілька бінів, то потрібно довабити унікальні ідентифікатори для цих бінів:
                        <bean id="pool1" class="com.study.spring.database.pool.ConnectionPool" />
                        <bean id="pool2" class="com.study.spring.database.pool.ConnectionPool" />
                        
                        
          коли ми добавляємо унікальні ідентифікатори   id, також можна передавати аліаси, які записуємо у  name, але вони також мають бути унікальними
          аліаси даємо тоді коли хочемо використати більш доцільну назву біна 
                        <bean id="pool1" name="p1, p10" class="com.study.spring.database.pool.ConnectionPool" />
                        <bean id="pool2" name="p2" class="com.study.spring.database.pool.ConnectionPool" />              
         */

        
        
//                System.out.println(context.getBean("pool1"));  // цей метод повертає Object
                System.out.println(context.getBean("pool1", ConnectionPool.class));  // цей метод повертає конкретний тип, тобто той клас, який ми вказали 2 параметром
        
        /*
               створити кілька однакових бінів, то у дебаг режимі можна зайти у beanDefinitionMap і побачимо, що там є кілька бінів ConnectionPool
                        context -> beanFactory -> beanDefinitionMap -> 
                            pool2 -> {GenericBeanDefinition@1642} "Generic bean: class [com.study.spring.database.pool.ConnectionPool]; scope=; abstract=false; lazyInit=false; autowireMode=0; dependencyCheck=0; autowireCandidate=true; primary=false; factoryBeanName=null; factoryMethodName=null; initMethodName=null; destroyMethodName=null; defined in class path resource [application.xml]"                            com.study.spring.database.pool.ConnectionPool#0 -> {GenericBeanDefinition@1648} "Generic bean: class [com.study.spring.database.pool.ConnectionPool]; scope=; abstract=false; lazyInit=false; autowireMode=0; dependencyCheck=0; autowireCandidate=true; primary=false; factoryBeanName=null; factoryMethodName=null; initMethodName=null; destroyMethodName=null; defined in class path resource [application.xml]"    
                            pool1 -> {GenericBeanDefinition@1643} "Generic bean: class [com.study.spring.database.pool.ConnectionPool]; scope=; abstract=false; lazyInit=false; autowireMode=0; dependencyCheck=0; autowireCandidate=true; primary=false; factoryBeanName=null; factoryMethodName=null; initMethodName=null; destroyMethodName=null; defined in class path resource [application.xml]"
                 
         */


    }
}
