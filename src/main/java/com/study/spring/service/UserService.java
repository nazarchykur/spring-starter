package com.study.spring.service;

import com.study.spring.database.repository.CompanyRepository;
import com.study.spring.database.repository.UserRepository;

public class UserService {
    
    /*
    не створюємо жорстку прив'язку, що наш сервіс сам створює і ініціалізує свої залежності
                
                private final  UserRepository userRepository = new UserRepository();
                
     навпаки, потрібно просто перерахувати які саме залежності нам потрібні 
     і тільки представити спосіб як їх можна ініціалізувати:
            1) через конструктор
            2) через сетер
            3) через поле     
            
     тепер сервіс не має додаткових зобов'язань щодо створення своїх залежностей              
     */
    private final  UserRepository userRepository;
    private final CompanyRepository companyRepository;

    public UserService(UserRepository userRepository, CompanyRepository companyRepository) {
        this.userRepository = userRepository;
        this.companyRepository = companyRepository;
    }
}
