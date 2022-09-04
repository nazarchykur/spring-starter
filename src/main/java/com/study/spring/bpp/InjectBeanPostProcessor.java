package com.study.spring.bpp;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.util.ReflectionUtils;

import java.util.Arrays;

public class InjectBeanPostProcessor implements BeanPostProcessor, ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        Arrays.stream(bean.getClass().getDeclaredFields())
                .filter(filter -> filter.isAnnotationPresent(InjectBean.class))
                .forEach(field -> {
                    Object beanToInject = applicationContext.getBean(field.getType());
//                    field.setAccessible(true);
//                    field.set(bean, beanToInject); // так як це рефлекшн апі, то тут багато перевірок НЕ рантайм і їх потрібно опрацьовувати
                    
                    ReflectionUtils.makeAccessible(field);
                    ReflectionUtils.setField(field, bean, beanToInject);
                });
        // так як у BeanPostProcessor#postProcessBeforeInitialization(bean, beanName)
//            завжди краще повертати БІН, то в даному випадку можна зразу його повернути 
//        return BeanPostProcessor.super.postProcessBeforeInitialization(bean, beanName);
        return bean;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
