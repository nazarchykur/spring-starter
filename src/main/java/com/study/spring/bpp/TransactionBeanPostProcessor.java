package com.study.spring.bpp;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

public class TransactionBeanPostProcessor implements BeanPostProcessor {

    private final Map<String, Class<?>> transactionBeans = new HashMap<>();

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
//
//        це було просто для демонстрації, чому тут не потрібно цього робити з ПРОКСІ
//                
//        if (bean.getClass().isAnnotationPresent(Transaction.class)) {
//            /*
//            тут тепер потрібно повернути ПРОКСІ, щоб додати функціональності
//                    є 2 види проксі:
//                        - динаміна - Dynamic Proxy через реалізацію через інтерфейс (по замовчуванню є у джаві, і не потрібні додаткові бібліотеки)
//                        - через наслідування, але тут вже з використанням бібліотек (Cglib, Javaassit, Byte Buddy ...)
//                        
//                        
//                        це було просто для демонстрації, чому тут не потрібно цього робити
//                        
//             */
//            return Proxy.newProxyInstance(bean.getClass().getClassLoader(), bean.getClass().getInterfaces(), (o, method, args) -> {
//                System.out.println("open transaction");
//                try {
//                    return method.invoke(bean, args);
//                } finally {
//                    System.out.println("close transaction");
//                }
//            });
//            
//        }
        if (bean.getClass().isAnnotationPresent(Transaction.class)) {
            transactionBeans.put(beanName, bean.getClass());
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        Class<?> beanClass = transactionBeans.get(beanName);
        if (beanClass != null) {
            /*
            тут тепер потрібно повернути ПРОКСІ, щоб додати функціональності
                    є 2 види проксі:
                        - динаміна - Dynamic Proxy через реалізацію через інтерфейс (по замовчуванню є у джаві, і не потрібні додаткові бібліотеки)
                        - через наслідування, але тут вже з використанням бібліотек (Cglib, Javaassit, Byte Buddy ...)
                        
             */
            return Proxy.newProxyInstance(beanClass.getClassLoader(), beanClass.getInterfaces(), 
                    (proxy, method, args) -> {
                System.out.println("open transaction");
                try {
                    return method.invoke(bean, args);
                } finally {
                    System.out.println("close transaction");
                }
            });
        }
        return bean;
    }
}
