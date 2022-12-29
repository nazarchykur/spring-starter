package com.study.spring.service;

import com.study.spring.database.entity.Company;
import com.study.spring.database.repository.CrudRepository;
import com.study.spring.dto.CompanyReadDto;
import com.study.spring.listener.entity.AccessType;
import com.study.spring.listener.entity.EntityEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/*
        @Transactional // !!! from Spring
        
        1) анотацію можна ставити над класом = тоді буде для всіх ПАБЛІК методів
        2) над методом - тобто тільки над цим ПАБЛІК методом
        
         - без @Transactional анотації можна запустити у дебаг режимі цей сервіс або тест і перевірити що викликається САМ КЛАС
             і його методи 
         - тепер з @Transactional = бачимо вже ПРОКСІ нашого сервісу
        
        
        коли викликається метод нашого сервісу, то викликається його CGLib ПРОКСІ, де до методу додається логіка з транзакцією:
         - 1) перед методом відкривається транзакція
         - 2) виклик реального метода нашого сервісу  - тобто та бізнес логіка яка була у нашому сервісі
         - 3) після методу закривається транзакція :
                - commit()    - якщо все добре
                - rollback()  - якщо помилка очікувана або неочікувана 
                
         !!! ВАЖЛИВО пам'ятати, що транзакція працює у межах CGLib ПРОКСІ потрібного класу сервісу
           так як робота з транзакціями зазвичай створюється на рівні сервісів
           
           якщо викликається метод БЕЗ анотації @Transactional, а всередині нього він вже викликає метод з @Transactional,
           то ніякої транзакції НЕ буде, оскільки викличеться спочатку метод, який НЕ ПРОКСОВАНИЙ, тобто тут 
           не має звідки взятися додатковому коду щодо транзакції
           
           якщо є депенденсі на інший сервіс який також має @Transactional або його методи, то щодо транзакції
           буде використовуватися так звана цепочка викликів CGLib ПРОКСІ
           бо відкриття транзакції відбулося наприклад у методі з першого сервісу, то у наступному сервісі ця
           транзакція перевикористається, ну і закриється відповідно тоже після того першого метода
           тобто той хто відкрив транзакцію, той нею і керує = відбувається відслідковування наступними CGLib ПРОКСІ чи 
           відкрита/закрита транзакція, щоб відповідно робити наступні дії 
           
           
           ВИСНОВОК:
           транзакції нам потрібні для того, щоб якась логіка щодо БД (всі КРАД операції = кілька однакових або різних)
            відбулася як одна пачка, або взагалі нічого не відбулося (наприклад добавити запис у одній таблиці + обновити
            у другій, або у другій тоже добавити дані, або видалив з кількох таблиць  і т.д.)
            
            
        !!! Важливе про Tранзакції і моменти коли НЕ спрацює:
             1) - якщо викликати анотований @Transactional метод у тому самому класі з НЕ анотованого
                        
                        class SomeClass {
                        
                             public method1(){
                                ...
                                method2()        - !!! NOT WORK
                                ...
                             }
                             
                             @Transactional
                             public method2(){
                                ...do something 
                             }
                        }
                        
                        
             - 2) опрацьовувати не всі  Exception  (За замовчуванням відкат відбувається лише для RuntimeException і Error)
                     - якщо впаде на якомусь іншому Exception то НЕ спрацює транзакція
                     
                        @Transactional(rollbackFor = StripeException.class)                       
                        public void createBillingAccount(Account acc) throws StripeException {
                            accSrvc.createAccount(acc);
                        
                            stripeHelper.createFreeTrial(acc);
                        }
             
            -3) Transaction isolation levels and propagation
                    Рівні ізоляції транзакцій і розповсюдження
                    
                    Часто розробники додають анотації, не думаючи про те, якої поведінки вони хочуть досягти. 
                    READ_COMMITED майже завжди використовує стандартний рівень ізоляції.

                    Розуміння рівнів ізоляції має важливе значення, щоб уникнути помилок, які пізніше дуже важко виправити.
                    
                    Наприклад, якщо ви створюєте звіти, ви можете вибрати різні дані на стандартному рівні ізоляції, 
                    виконавши той самий запит кілька разів під час транзакції. Це трапляється, коли паралельна транзакція 
                    щось фіксує в цей час. Використання REPEATABLE_READ допоможе уникнути подібних сценаріїв і заощадить 
                    багато часу на усунення несправностей.
                    
                    Різні розповсюдження (propagation) допомагають пов’язувати транзакції в нашій бізнес-логіці. Наприклад, якщо вам 
                    потрібно запустити якийсь код в іншій транзакції, а не у зовнішній, ви можете використати REQUIRES_NEW
                    розповсюдження, яке призупиняє зовнішню транзакцію, створює нову, а потім відновлює зовнішню транзакцію.
                    
                    
             - 4) працює тільки для PUBLIC методів  =  бо працює через CGLib ПРОКСІ
             
             - 5) займаз з'єднання з БД поки не закінчиться транзакція
                   - краще виносити методи які довго працюють  поза межами транзакції      
 */

/*
    TransactionalAutoConfiguration
    
    * так як тут використовується Spring Boot, то через цю TransactionalAutoConfiguration у нас вже налаштований TransactionalManager
            
            саме тут бачимо JdkDynamicAutoProxyConfiguration і CglibAutoProxyConfiguration 
            
            так як ми вже знаємо, що через проксі можемо добавляти новий функціонал до вже створеного класу
            
    * без автоконфігурації (без Spring Boot) потрібно використовувати анотацію @EnableTransactionalManager вручну,
      щоб включити механізм опрацювання @Transactional
      
      
 */

@Service
@RequiredArgsConstructor
@Transactional // !!! from Spring
public class CompanyService {
    private final CrudRepository<Integer, Company> companyRepository;
    private final UserService userService;
    private final ApplicationEventPublisher eventPublisher;


    public Optional<CompanyReadDto> findById(Integer id) {
        return companyRepository.findById(id)
                .map(entity -> {
                    eventPublisher.publishEvent(new EntityEvent(entity, AccessType.READ));
                    return new CompanyReadDto(entity.getId());
                });
    }

}
