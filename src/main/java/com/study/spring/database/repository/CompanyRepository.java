package com.study.spring.database.repository;

import com.study.spring.bpp.Auditing;
import com.study.spring.bpp.Transaction;
import com.study.spring.database.entity.Company;
import com.study.spring.database.pool.ConnectionPool;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Optional;

@Slf4j
@Repository
@Transaction
@Auditing
@RequiredArgsConstructor
public class CompanyRepository implements CrudRepository<Integer, Company> {
    @Qualifier("pool1")
    private final ConnectionPool connectionPool;
    private final List<ConnectionPool> pools;
    @Value("${db.pool.size}")
    private final Integer poolSize;

    @PostConstruct
    public void init() {
        log.info("Init company repository");
    }

    @Override
    public Optional<Company> findById(Integer id) {
        log.info("findById() ...");
        return Optional.of(new Company(id));
    }

    @Override
    public void delete(Company entity) {
        log.info("delete() ...");
    }
}
