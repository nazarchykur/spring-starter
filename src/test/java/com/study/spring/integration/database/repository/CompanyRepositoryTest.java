package com.study.spring.integration.database.repository;

import com.study.spring.database.entity.Company;
import com.study.spring.integration.annotaion.IT;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Commit;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@IT
@RequiredArgsConstructor
@Transactional // ВАРТО ПАМЯТАТИ використовувати потрібно спрінгову анотацію (не від JavaEE javax.transaction)
//@Rollback // після кожного тесту буде відкат транзакції = що і потрібно майже завжди для тесту
//@Commit // якщо все ж потрібно закомітити операції
class CompanyRepositoryTest {
    @Autowired
    private EntityManager entityManager;

    @Test
    void findById() {
        Company company = entityManager.find(Company.class, 1);
        assertNotNull(company);
        assertThat(company.getLocales()).hasSize(2);
        /*
        потрібно добавити @Transactional, або відкрити і закрити транзакцію
        бо у момент виклику getLocales() буде ексепшн  LazyInitializationException:
            Unable to evaluate the expression Method threw 'org.hibernate.LazyInitializationException' exception.
        тому що тут мав би піти другий запит до БД, щоб взяти ці  getLocales
        
        
        тепер ми маємо побачити 2 виклики до БД
                Hibernate: 
                select
                    company0_.id as id1_1_0_,
                    company0_.name as name2_1_0_ 
                from
                    company company0_ 
                where
                    company0_.id=?
                    
                Hibernate: 
                select
                    locales0_.company_id as company_1_2_0_,
                    locales0_.description as descript2_2_0_,
                    locales0_.lang as lang3_0_ 
                from
                    company_locales locales0_ 
                where
                    locales0_.company_id=?
                    
                      
         */
    }
/*
    // щоб показати як працює анотація   @Commit
    // !!! при повторному зберіганні впаде, бо у БД вже буде ця компанія

    @Test
    void saveNewCompany() {
        Map<String, String> locales = new HashMap<>();
        locales.put("ua", "Apple опис");
        locales.put("en", "Apple description");
        Company company = Company.builder()
                .name("Apple")
                .locales(locales)
                .build();

        entityManager.persist(company);
        assertNotNull(company.getId());
    }
 */
}