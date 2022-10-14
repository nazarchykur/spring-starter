package com.study;

import com.study.spring.config.DatabaseProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/*
    hibernate
        - DAO
        - @Transactional
        - Configuration

    з ORM framework  hibernate
        - ми все ж самі маємо писати нащ ДАО слой = Репозіторі,
            хоча hibernate надає нам безліч свого функціонала, але все ж для нашого Ентіті, нам потрібно писати ДАО
        
        - hibernate не надає Транзакшн менеджера для того щоб опрацьовувати наші транзакції в деклеративному стилі,
            тобто hibernate надає @Transactional, але нам самим потрібно писати Транзакшн менеджер, для того щоб 
            автоматично відкривати та закривати транзакцію
        
        - конфігурація доволі тяжка, потрібно працювати з XML, вручну створювати SessionFactory і т.д.
        
     
     тому нам на допомогу приходить   Spring Dara JPA
        так як hibernate є практично стандарт дефакто, то JPA надає імплементацію, щоб максимально спростити слой 
        звязаний з доступом до БД
         
        
        - для того щоб не реалізовувати слой ДАО, спрінг надає інтерфейс    Repository, 
            і у нас готовий ДАО для кожного нашого Ентіті
            
        - для того щоб не писати свій TransactionManager, тут він вже є готовий  
       
        -  Configuration  = тут допомагає спрінг AutoConfiguration,
            тобто в залежності від Condition додається відповідна автоконфігурація, і все що потрібно, це додати
            у файлі пропертіс (або ямл) потрібні налаштування
     
    ====================================================================================================================
   
    ми будемо працювати з postgres , тому щоб було простіше встановимо докер і створимо образ
        
        встановлюємо докер   https://docs.docker.com/engine/install/ubuntu/
    
        створимо образ https://hub.docker.com/_/postgres
            
 */

/*
    Transactional
    
     Отже ми бачимо як просто налаштовувати тепер JPA/Hibernate з допомогою файла пропертіс
     
     так як у Spring Boot в нас багато автоконфігурується, то з залежністю 
            implementation 'org.springframework.boot:spring-boot-starter-data-jpa'
            
     залишається через передати кілька основних полів через пропертіс і все готово
     
     
     
     Розглянемо як відбувається робота з транзакціями       
     
     TransactionManager
        TransactionManager автоконфігурується через JpaBaseConfiguration
        
        TransactionManager можна використовувати кількома способами:
            - деклеративний з допомогою анотації @Transactional  ( простий і у більшості випадків цей спосіб будемо використовувати)
            - вручну через TransactionTemplate, тобто так, як ми управляємо в Hibernate, коли самі відкриваємо і закриваємо/ревертаємо зміни
 */

/*
    Отже як працює Transaction
    Transaction працює через AOP
    
        TransactionAutoConfiguration
        
        1) можна глянути що вона спрацьовує тоді коли
            
            @ConditionalOnClass(PlatformTransactionManager.class) 
        який створюється у Hibernate автоконфігурації
        
        2) і після того як  
            @AutoConfigureAfter({ JtaAutoConfiguration.class, HibernateJpaAutoConfiguration.class,
		        DataSourceTransactionManagerAutoConfiguration.class, Neo4jDataAutoConfiguration.class })
		    тобто якщо ми працюємо з   Hibernate, то можна добавити як ми будемо працювати з   TransactionManager (деклеративний або вручну)
		    
        3) @EnableConfigurationProperties(TransactionProperties.class) тут можна подивитися на TransactionProperties
         які налаштовуються через префікс   spring.transaction
                @ConfigurationProperties(prefix = "spring.transaction")
                
        4) далі бачимо основні класи:
            -  TransactionTemplate    для ручного управління транзакціями
            
            - EnableTransactionManagementConfiguration    який налаштовує через упраління через анотацію  @Transactional
            
         
         !!! без автоконфігурації (тобто без Spring Boot) потрібно використовувати 
                    @EnableTransactionManagement   -   щоб включити вручну цей механізм опрацювання    @Transactional
              
            отже у EnableTransactionManagementConfiguration  бачимо що через ПРОКСІ   
            за допомогою якого ми можемо добавляти новий функціонал на вже існуючі класи  
            
             - JdkDynamicAutoProxyConfiguration   - по замовчуванні виключений
             - CglibAutoProxyConfiguration   - включений   (працює тільки для ПАБЛІК методів)
                            
 */


@SpringBootApplication
@ConfigurationPropertiesScan
public class AppRunner {
    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(AppRunner.class, args);
        System.out.println(context.getBeanDefinitionCount());
        System.out.println(context.getBean("pool1"));
        System.out.println(context.getBean(DatabaseProperties.class));
    }

    /*
     
     https://spring.io/projects/spring-boot
     
     https://www.pranaybathini.com/2021/07/understanding-spring-framework-and-ecosystem.html
                    
      Spring Framework – екосистема та огляд
      
         Коли люди говорять про Spring, вони мають на увазі всю екосистему проектів Spring, як-от spring framework, 
         spring boot, spring data, spring cloud, spring batch та багато інших проектів.
         
         Spring виник у відповідь на J2EE, щоб значно спростити розробку та доступ до даних. 
         Це також спрямовано на зменшення бойлер коду (повторюваний код, який часто потрібен і захаращує програму => 
         щоб сфокусуватися тільки на основній логіці).
         Успіх Spring Framework призвів до створення інших Spring-проектів, які будуються на його основі.
         Приклад: spring data полегшує доступ до даних. 
         Spring security обробляє аспекти безпеки програми Java.
         
         Spring boot справді змінює правила гри. 
         До Spring boot створення програми Spring вимагало багато вибору, конфігурації та громіздкої моделі розгортання.
         Spring boot видалив усі нудні аспекти Spring і включив розумні автоматичні налаштування за замовчуванням для 
         вибору бібліотеки, конфігурації та заглушок для автоматичного визначення та додавання загальної конфігурації.
         Це також значно спростило процес розгортання, запустивши просту команду.
         Spring Cloud було побудовано на основі Spring Boot і спрощує створення програм, які використовують розподілену архітектуру.                    
    
    
    
         Spring Framework проти Spring boot
            Spring boot дозволяє розробникам швидко та легко вивчити його. 
            
            
         Notable Features
            - Auto-configuration 
            - Stand alone - do not need to deploy to webserver. Just run it with a command.
            - Opinionated - default settings are opinion of majority.   
            
            
         Auto-configuration
             - Намагається автоматично налаштувати вашу програму для завантаження Spring на основі доданих вами функцій.
             - Автоматичні конфігурації розумні та з урахуванням контексту. напр. Якщо програма має залежність від бази даних, 
                вона намагається налаштувати програму для доступу до бази даних.
             - Налаштувати автоматичну конфігурацію легко. Просто додайте анотацію @EnableAutoConfiguration. 
                Думайте про анотації як про додатковий код, який читайте під час виконання, щоб приймати рішення.
             - Крім того, конфігурації також легко вимкнути.
             
         Stand-alone    
            - Відповідно до проекту spring boot, " Spring boot полегшує створення автономних програм на основі Spring, які можна просто запускати ".
            - Упаковка вашої програми (war / jar)
            - Завантаження веб-сервера – різні варіанти з різними функціями.
            - Прийнявши рішення, завантажте та налаштуйте веб-сервер. Залежно від сервера може бути складним або простим.
            - Нарешті, розгорніть програму (скопіюйте в певний каталог) і запустіть веб-сервер.
         
         Opinionated view   
            - Під час створення додатків Java у нас є маса варіантів, таких як вибір журналювання, вибір конфігурації, 
                вибір інструментів, вибір інструментів для створення тощо.
            - Розробник може будь-коли перезаписати будь-який із варіантів.
            
            
         Spring Projects
            - There are 22 spring projects active now.  Few of them are
            
            - Spring boot    - Spring Boot makes it easy to create stand-alone, production-grade spring based 
                Applications that you can "just run". No need of any extra configurations.
            - Spring Data   - Spring Data provides a familiar and consistent, Spring-based programming model for 
                data access while still retaining the special features of the underlying data store.
            - Spring Cloud - Spring Cloud provides tools for developers to quickly build some of the 
                common patterns in distributed systems like configuration management, service discovery,    
                circuit breakers, intelligent routing, micro-proxy, control bus, one-time tokens, global locks,     
                leadership election, distributed sessions, cluster state. Coordination of distributed systems   
                leads to boiler plate patterns, and using Spring Cloud developers can quickly stand up services 
                and applications that implement those patterns. They will work well in any distributed environment,     
                including the developer’s own laptop, bare metal data centres.
            - Spring Security - Spring Security is a powerful and highly customizable authentication and access-control 
                framework which is the de-facto standard for securing Spring-based applications.
            - Spring Batch  - A lightweight batch framework designed to enable the development of robust batch      
                applications vital for the daily operations of enterprise systems.
               
     */
}
