package com.study;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/*
    Одну інструкцію @SpringBootApplication можна використовувати для включення цих трьох функцій, а саме:

    @EnableAutoConfiguration: увімкнути механізм автоконфігурації Spring Boot
    @ComponentScan: увімкнути сканування @Component для пакета, в якому знаходиться програма
    @Configuration: дозволяє реєструвати додаткові компоненти (beans) у контексті або імпортувати додаткові класи конфігурації
    
        Жодна з цих функцій не є обов'язковою, і ви можете замінити цю єдину інструкцію будь-якої з функцій, які вона включає. 
        Наприклад, ви можете не захотіти використовувати сканування компонентів або сканування властивостей конфігурації у вашій програмі:
                @Configuration(proxyBeanMethods = false)
                @EnableAutoConfiguration
                @Import({ MyConfig.class, MyAnotherConfig.class })
                public class Application {
                    public static void main(String[] args) {
                        SpringApplication.run(Application.class, args);
                    }
                }

    зазвичай  клас який позначений  @SpringBootApplication  має знаходитися в рутовому пакеті на рівні всіх інших папок з підпапками
              
                 com.example
                     +- myapplication
                         +- Application.java           <==
                         |
                         +- customer
                         |   +- Customer.java
                         |   +- CustomerController.java
                         |   +- CustomerService.java
                         |   +- CustomerRepository.java
                         |
                         +- order
                             +- Order.java
                             +- OrderController.java
                             +- OrderService.java
                             +- OrderRepository.java
                             
        це для того щоб автосканувати вміст всіх папок для знаходження компонентів (всіх БІНІВ нашої програми)                      
 */

/*
     Properties
        
        https://docs.spring.io/spring-boot/docs/current/reference/html/application-properties.html
        
        application.properties використовуються для збереження «N» кількості властивостей в одному файлі для запуску програми. 
        У Spring Boot властивості зберігаються у файлі application.properties у шляху до класу.   
        
            Файл application.properties знаходиться в каталозі src/main/resources . Зразок:
                    server.port = 9090
                    spring.application.name = demoservice
                    
        YAML File
            Spring Boot підтримує налаштування властивостей на основі YAML для запуску програми. 
            Замість application.properties ми можемо використовувати файл application.yml .
                    spring:
                       application:
                          name: demoservice
                    server:
                       port: 8080                      
       =================================================================================================================             
           Використання анотації @Value
                Анотація @Value використовується для читання значення властивості середовища або програми в коді Java. 
                Синтаксис: 
                        @Value("${property_key_name}")
                приклад, у якому показано синтаксис читання значення властивості spring.application.name у змінній Java за допомогою анотації @Value.
                        @Value("${spring.application.name}")     
                        
            
       =================================================================================================================                 
          Активний профіль Spring Boot
          
            Spring Boot підтримує різні властивості на основі активного профілю Spring. 
            Наприклад, ми можемо зберегти два окремих файли для розробки та виробництва для запуску програми Spring Boot.
            
            Spring активний профіль у application.properties
            Давайте розберемося, як мати активний профіль Spring у application.properties. 
            За замовчуванням   application.properties   будуть використовуватися для запуску програми Spring Boot. 
            Якщо ви хочете використовувати властивості на основі профілю, ми можемо зберегти окремий файл властивостей 
            для кожного профілю, як показано нижче −    
            
            application.properties
                    server.port = 8080
                    spring.application.name = demoservice
            -------------------------------------------------------------------------------        
            application-dev.properties
                    server.port = 9090
                    spring.application.name = demoservice
            ------------------------------------------------------------------------------- 
            application-prod.properties
                    server.port = 4431
                    spring.application.name = demoservice              
 */

/*
    https://docs.spring.io/spring-boot/docs/current/reference/html/features.html#features.external-config
            
            Spring Boot uses a very particular PropertySource order that is designed to allow sensible overriding of values. 
            Properties are considered in the following order (with values from lower items overriding earlier ones):

        1. Default properties (specified by setting SpringApplication.setDefaultProperties).
        
        2. @PropertySource annotations on your @Configuration classes. Please note that such property sources are not added to the Environment until the application context is being refreshed. This is too late to configure certain properties such as logging.* and spring.main.* which are read before refresh begins.
        
        3. Config data (such as application.properties files).
        
        4. A RandomValuePropertySource that has properties only in random.*.
        
        5. OS environment variables.
        
        6. Java System properties (System.getProperties()).
        
        7. JNDI attributes from java:comp/env.
        
        8. ServletContext init parameters.
        
        9. ServletConfig init parameters.
        
        10. Properties from SPRING_APPLICATION_JSON (inline JSON embedded in an environment variable or system property).
        
        11. Command line arguments.
        
        12. properties attribute on your tests. Available on @SpringBootTest and the test annotations for testing a particular slice of your application.
        
        13. @TestPropertySource annotations on your tests.
        
        14. Devtools global settings properties in the $HOME/.config/spring-boot directory when devtools is active.
        
     !!!   
     тобто визначивши ту саму проперті одним із способів (з цих 14), значення перезапишеться тим що йде нижче по списку  
     
     
     
     Config data files are considered in the following order:

        1. Application properties packaged inside your jar (application.properties and YAML variants).
        
        2. Profile-specific application properties packaged inside your jar (application-{profile}.properties and YAML variants).
        
        3. Application properties outside of your packaged jar (application.properties and YAML variants).
        
        4. Profile-specific application properties outside of your packaged jar (application-{profile}.properties and YAML variants).
        
        
        щоб подивитися що значення справді перезаписуються добавимо змінні через Intellij  -> Edit Configuration
            
      ==================================================================================================================
      
      Spring Boot - Using ${} placeholders in Property Files
      
        We just need to use ${someProp} in property file and start the application having 'someProp' in system 
        properties or as main class (or jar) argument '--someProp=theValue'.

        This feature allows us to use 'short' command line arguments.
                app.title=Boot ${app}


      
 */
@SpringBootApplication
public class AppRunner {
    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(AppRunner.class, args);
        System.out.println(context.getBeanDefinitionCount());
        System.out.println(context.getBean("pool1"));
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
