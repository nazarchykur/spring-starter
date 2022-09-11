package com.study.spring.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

import java.util.List;

@ConfigurationProperties(prefix = "db")
@ConstructorBinding
@Getter
@Setter
public class DatabaseProperties {
    private String username;
    private String password;
    private String driver;
    private String url;
    private String hosts;
    private PoolProperties pool;
    private List<PoolProperties> pools;

    @Getter
    @Setter
    public static class PoolProperties {
        private Integer size;
        private Integer timeout;
    }
}
