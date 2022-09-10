package com.study.spring.integration.service;

import com.study.spring.dto.CompanyReadDto;
import com.study.spring.integration.annotaion.IT;
import com.study.spring.service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

//@TestPropertySource("classpath:application.yml") // не працює бо це YAML файл,  якщо це було би пропертіс, то ОК

//@ContextConfiguration(classes = AppRunner.class, initializers = ConfigDataApplicationContextInitializer.class)  // від цього класу буде працювати YAML файл (ця залежність у папці тест)
//@ExtendWith(SpringExtension.class)

//@SpringBootTest // замість  @ExtendWith + @ContextConfiguration


@IT // можна створити свою анотацію = @SpringBootTest  + @ActiveProfiles("test")
//@RequiredArgsConstructor
public class CompanyServiceIT {
    public static final Integer COMPANY_ID = 1;
    @Autowired
    private CompanyService companyService;

    @Test
    void findById() {
        Optional<CompanyReadDto> actualResult = companyService.findById(COMPANY_ID);

        assertTrue(actualResult.isPresent());

        CompanyReadDto expectedResult = new CompanyReadDto(COMPANY_ID);
        actualResult.ifPresent(actual -> assertEquals(expectedResult, actual));
    }
}

//@ActiveProfiles("test")
