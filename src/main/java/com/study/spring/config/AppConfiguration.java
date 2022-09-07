package com.study.spring.config;

import com.study.web.config.WebConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;

/*
        видаляємо з xml файла    <context:property-placeholder location="classpath:application.properties"/>   
                і замінюємо на   @PropertySource("classpath:application.properties")
            
            
         видаляємо з xml файла    <context:component-scan base-package="com.study.spring"/>    
                і замінюємо на    @ComponentScan(basePackages = "com.study.spring")
                
                також можна додатково налаштувати , ось приклад ще кількох атрибутів
                        @ComponentScan(basePackages = "com.study.spring",
                                useDefaultFilters = false,
                                includeFilters = {
                                        @Filter(type = FilterType.ANNOTATION, value = Component.class),
                                        @Filter(type = FilterType.ASSIGNABLE_TYPE, value = CrudRepository.class),
                                        @Filter(type = FilterType.REGEX, pattern = "com\\..+Repository")
                                })
 */

/*
    @ImportResource
        можна наприклад комбінувати наприклад + xml
        
        УВАГА: майже НЕ використовується, бо всі перейшли якраз на анотації
        
        @ImportResource("classpath:application.xml")
 */

/*
   @Import()
   
       зазвичай тут вказуються ті конфіги які ми не скануємо тут , 
       тобто це інші модулі, навіть у других джарках, інших залежностях які ми будемо підключати
       
       ось наприклад з іншого пакета, який ми не скануємо
       
            @Import(WebConfiguration.class)
            
            
    Анотація @Import у Spring дозволяє завантажувати визначення bean-компонентів (bean definitions) з одного чи кількох інших 
    @Configuration файлів або компонентів. 
    Можливо, вам не захочеться налаштовувати всі bean-компоненти в одному конфігураційному файлі. 
    Використовуючи @Import , ми можемо розділити конфігурації на кілька конфігураційних файлів 
    для модульування, що робить наш код чистішим і зручнішим для обслуговування.
    
    
    
    
    @ComponentScan: It tells Spring which packages/components to scan and find using configuration.

    @Import: It tells Spring which configurations/components import into another configuration.
    
    
    
    ====================================================================================================================
    
    
    Спочатку давайте створимо класи конфігурації, а потім імпортуємо їх до інших конфігурацій. 
    Тут ми створюємо файли конфігурації FishConfig і BirdConfig, у яких є визначення бінів GoldFish, Guppy, Salmon, Eagle, Ostrich, Peacock.
                                                |
    @Configuration                              |                 @Configuration
    public class FishConfig {                   |                 public class BirdConfig {
                                                |                 
      @Bean                                     |                    @Bean
      public GoldFish goldFish() {              |                    public Eagle eagle() { 
        return new GoldFish();                  |                       return new Eagle();
      }                                         |                     }
                                                |                                 
      @Bean                                     |                     @Bean
      public Guppy guppy() {                    |                     public Ostrich ostrich() {
        return new Guppy();                     |                        return new Ostrich();
      }                                         |                     }
                                                |                     
      @Bean                                     |                     @Bean
      public Salmon salmon() {                  |                     public Peacock peacock() {
        return new Salmon();                    |                      return new Peacock();    
      }                                         |                     }   
                                                |                           
    }                                           |                   }
    
    
    Тепер давайте імпортуємо файли FishConfig і BirdConfig в іншу конфігурацію під назвою ImportBeansConfig . 
    Анотація @Import також дозволяє групувати/імпортувати кілька конфігурацій .
    
                @Configuration
                @Import({FishConfig.class, BirdConfig.class})
                public class ImportBeansConfig {
                
                  @Bean
                  public ExampleBean exampleBean() {
                    return new ExampleBean();
                  }
                  
                  @Bean
                  public SampleBean sampleBean() {
                    return new SampleBean();
                  }
                }
                
                                                               |
    public class ExampleBean {                                 |    public class SampleBean {
                                                               |    
      @Autowired                                               |       @Autowired   
      private Salmon salmon;                                   |       private GoldFish goldFish; 
                                                               |        
      @Autowired                                               |       @Autowired
      private Guppy guppy;                                     |       private Eagle eagle;
                                                               |        
      @Autowired                                               |       @Autowired
      private Peacock peacock;                                 |       private Ostrich ostrich;
                                                               |        
      public void printObjects() {                             |       public void printObjects() {
        System.out.println("- Print ExampleBean Objects -");   |          System.out.println("- Print SampleBean Objects -");
        System.out.println(salmon);                            |          System.out.println(goldFish);
        System.out.println(guppy);                             |          System.out.println(eagle);
        System.out.println(peacock);                           |          System.out.println(ostrich);
      }                                                        |       }
    }                                                          |    }         
    
    
    
    public class ImportAnnotationConfigDemo {
      public static void main(String[] args) {
        ApplicationContext ctxt = new AnnotationConfigApplicationContext(ImportBeansConfig.class);
        ExampleBean exampleBean = ctxt.getBean(ExampleBean.class);
        SampleBean sampleBean = ctxt.getBean(SampleBean.class);
        
        exampleBean.printObjects();
        sampleBean.printObjects();
        
      }
    }
    
    Output Results:
            - Print ExampleBean Objects -
                Salmon
                Guppy
                Peacock
            - Print SampleBean Objects -
                GoldFish
                Eagle
                Ostrich
                
                
     ------------------------------------------------------------------------------------------------------------------
     Імпортування компонентів
     
     Анотація @Import дозволяє імпортувати певний компонент в іншу конфігурацію. 
     Ми можемо імпортувати групу/кілька компонентів. 
     Давайте подивимося приклад, у наступному прикладі імпортування ExampleComponent в ImportComponentsConfig , 
     який визначив bean під назвою ComponentsTestBean .   
     
                @Configuration
                @Import(ExampleComponent.class)
                public class ImportComponentsConfig {
                  @Bean
                  public ComponentsTestBean componentsTestBean() {
                    return new ComponentsTestBean();
                  }
                }        
                
      Давайте подивимося на існуючі компоненти/біни Spring у контексті програми.
       
                public class ImportComponetsConfigDemo {
                      public static void main(String[] args) {
                            ApplicationContext ctxt = new AnnotationConfigApplicationContext(ImportComponentsConfig.class);
                            String[] components = ctxt.getBeanDefinitionNames();
                            for (String bean : components)
                               System.out.println(bean);
                      }
                }
       
       На виході зможемо побачити, що ExampleComponent є у доступних компонентах у контексті програми.   
                        importComponentsConfig
                        com.study.spring.config.component.ExampleComponent
                        componentsTestBean
                        ..... 
                        ......other spring internal beans  
 */

@Import(WebConfiguration.class)
@Configuration
@PropertySource("classpath:application.properties")
@ComponentScan(basePackages = "com.study.spring")
public class AppConfiguration {
}
