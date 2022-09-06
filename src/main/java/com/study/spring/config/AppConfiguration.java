package com.study.spring.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
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
@Configuration
@PropertySource("classpath:application.properties")
@ComponentScan(basePackages = "com.study.spring")
public class AppConfiguration {
}
