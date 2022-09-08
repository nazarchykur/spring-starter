package com.study;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

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
@SpringBootApplication
public class AppRunner {
    public static void main(String[] args) {
        SpringApplication.run(AppRunner.class, args);
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
