package com.study.spring.integration.service;

import com.study.spring.integration.annotaion.IT;
import com.study.spring.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

//@SpringBootTest
@IT
public class UserServiceIT {

    @Autowired
    private UserService userService;

    @Test
    void test() {
    }
}
