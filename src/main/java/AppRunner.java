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
        
        /*
               у дебаг режимі перевіряємо  ConnectionPool
               
                        IMEGES
                            resources/images/lesson_6_xml-based_constructor-injection/constructor-injection.png
                            
                            
                       connectionPool
                            username = postgress
                            poolSize = 10
                            args    
                                0 = --arg1=value1
                                1 = --arg2=value2
                            properties
                                url -> postgresurl
                                password -> 123
         */

        
        /*
        IMEGES
            resources/images/lesson_6_xml-based_constructor-injection/construction-injection_indexedArgumentValues.png

        context -> 
                beanFactory -> 
                        beanDefinitionMap ->
                            pool1 -> {GenericBeanDefinition@1796} "Generic bean: class [com.study.spring.database.pool.ConnectionPool]; scope=; abstract=false; lazyInit=false; autowireMode=0; dependencyCheck=0; autowireCandidate=true; primary=false; factoryBeanName=null; factoryMethodName=null; initMethodName=null; destroyMethodName=null; defined in class path resource [application.xml]"
                            ->
                                constructorArgumentValues -> 
                                    indexedArgumentValues ->
                                        {Integer@1799} 0 -> {ConstructorArgumentValues$ValueHolder@1800}
                                        {Integer@1801} 3 -> {ConstructorArgumentValues$ValueHolder@1802}
                                        {Integer@1803} 2 -> {ConstructorArgumentValues$ValueHolder@1804}
                                        {Integer@1805} 1 -> {ConstructorArgumentValues$ValueHolder@1806}
         */
        
         /*
        IMEGES
            resources/images/lesson_6_xml-based_constructor-injection/construction-injection_genericArgumentValues.png

        context -> 
                beanFactory -> 
                        beanDefinitionMap ->
                            pool1 -> {GenericBeanDefinition@1796} "Generic bean: class [com.study.spring.database.pool.ConnectionPool]; scope=; abstract=false; lazyInit=false; autowireMode=0; dependencyCheck=0; autowireCandidate=true; primary=false; factoryBeanName=null; factoryMethodName=null; initMethodName=null; destroyMethodName=null; defined in class path resource [application.xml]"
                            ->
                                constructorArgumentValues -> 
                                    indexedArgumentValues -> 0
                                    genericArgumentValues ->
                                        0 - username
                                        1 - properties
                                        2 - args
                                        3 - poolSize
         */

    }
}
