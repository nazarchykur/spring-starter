package com.study.spring.listener.entity;

import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/*
    EventListener
    
    1) Що таке прослуховувачі подій Spring Boot?
        
        Анотація для прослуховувачів подій Spring Boot - це Метод, який прослуховує події завантаження Spring, 
        створюється за допомогою @EventListener. 
        Клас ApplicationEventPublisher використовується для трансляції події Spring завантаження. 
        Коли подія публікується з ApplicationEventPublisher класом, тоді викликаються анотовані методи @EventListener.
        
               Слухача можна визначити двома способами:
                - Можна використовувати анотацію @EventListener 
                - ApplicationListener інтерфейс.
    
    2) Що таке слухач у обробці подій?
        Коли відбувається подія, об’єкт слухача отримує сповіщення.

    3) Чи можливо мати Publisher в одній програмі Spring Boot і Listener в іншій програмі?
        Видавцем і слухачем EventListener можна керувати лише в одній програмі Spring. Нам потрібно буде 
        використовувати зовнішню чергу повідомлень, щоб розповсюдити їх по мережі (наприклад, Kafka, RabbitMQ).
 */

@Component
public class EntityListener {

    @EventListener
    @Order(10)
    public void acceptEntity(EntityEvent entityEvent) {
        System.out.println("Entity: " + entityEvent);
    }
}
