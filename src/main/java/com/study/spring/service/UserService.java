package com.study.spring.service;

import com.study.spring.database.entity.Company;
import com.study.spring.database.repository.CrudRepository;
import com.study.spring.database.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final CrudRepository<Integer, Company> companyRepository;
}
