package com.study.spring.database.repository;

import com.study.spring.bpp.Auditing;
import com.study.spring.bpp.InjectBean;
import com.study.spring.bpp.Transaction;
import com.study.spring.database.entity.Company;
import com.study.spring.database.pool.ConnectionPool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

@Transaction
@Auditing
public class CompanyRepository implements CrudRepository<Integer, Company> {
//якщо використовувати цю анотацію (менше використовується)
//    @Resource(name="pool1")

    /*
    можна вказати назву поля щоб обійтися без  @Qualifier
    @Autowired
    private ConnectionPool pool1;
     */
    
    @Autowired
    @Qualifier("pool1")
    private ConnectionPool connectionPool;

    @Autowired
    private List<ConnectionPool> pools;

    @Value("${db.pool.size}")
    private Integer poolSize;

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
