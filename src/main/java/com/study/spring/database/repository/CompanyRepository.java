package com.study.spring.database.repository;

import com.study.spring.bpp.InjectBean;
import com.study.spring.database.pool.ConnectionPool;

public class CompanyRepository {

    @InjectBean
    private ConnectionPool connectionPool;

}
