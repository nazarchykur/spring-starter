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
 */

@Import(WebConfiguration.class)
@Configuration
@PropertySource("classpath:application.properties")
@ComponentScan(basePackages = "com.study.spring")
public class AppConfiguration {
}
