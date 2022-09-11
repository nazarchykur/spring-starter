package com.study.spring.service;

import com.study.spring.database.entity.Company;
import com.study.spring.database.repository.CrudRepository;
import com.study.spring.dto.CompanyReadDto;
import com.study.spring.listener.entity.EntityEvent;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@ExtendWith(MockitoExtension.class)
class CompanyServiceTest {
    
    public static final Integer COMPANY_ID = 1;

    @Mock
    private CrudRepository<Integer, Company> companyRepository;
    @Mock
    private UserService userService;
    @Mock
    private ApplicationEventPublisher eventPublisher;

    @InjectMocks
    private CompanyService companyService; 

    @Test
    void findById() {
        doReturn(Optional.of(new Company(COMPANY_ID,null, Collections.emptyMap()))).when(companyRepository).findById(COMPANY_ID);

        Optional<CompanyReadDto> actualResult = companyService.findById(COMPANY_ID);
        
        assertTrue(actualResult.isPresent());

        CompanyReadDto expectedResult = new CompanyReadDto(COMPANY_ID);
        
        actualResult.ifPresent(actual -> assertEquals(expectedResult, actual));

        verify(companyRepository).findById(COMPANY_ID);
        verify(eventPublisher).publishEvent(any(EntityEvent.class));
        verifyNoMoreInteractions(companyRepository, eventPublisher, userService);
    }
}