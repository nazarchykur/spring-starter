package com.study.spring.database.repository;

import com.study.spring.bpp.Auditing;
import com.study.spring.bpp.InjectBean;
import com.study.spring.bpp.Transaction;
import com.study.spring.database.entity.Company;
import com.study.spring.database.pool.ConnectionPool;

import javax.annotation.PostConstruct;
import java.util.Optional;

@Transaction
@Auditing
public class CompanyRepository implements CrudRepository<Integer, Company> {

    @InjectBean
    private ConnectionPool connectionPool;

    @PostConstruct
    public void init() {
        System.out.println("Init company repository");
    }

    @Override
    public Optional<Company> findById(Integer id) {
        System.out.println("findById() ...");
        return Optional.of(new Company(id));
    }

    @Override
    public void delete(Company entity) {
        System.out.println("delete() ...");
    }
}
