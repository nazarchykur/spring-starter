package com.study.spring.database.repository;

import com.study.spring.bpp.Auditing;
import com.study.spring.bpp.Transaction;
import com.study.spring.database.entity.Company;
import com.study.spring.database.pool.ConnectionPool;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Optional;

@Repository
@Transaction
@Auditing
public class CompanyRepository implements CrudRepository<Integer, Company> {

    private final ConnectionPool connectionPool;
    private final List<ConnectionPool> pools;
    private final Integer poolSize;

    public CompanyRepository(@Qualifier("pool1") ConnectionPool connectionPool,
                             List<ConnectionPool> pools,
                             @Value("${db.pool.size}") Integer poolSize) {
        this.connectionPool = connectionPool;
        this.pools = pools;
        this.poolSize = poolSize;
    }

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
