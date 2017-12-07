package com.frank.config;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;


@Configuration
@EntityScan(basePackages = {"com.frank.entity"})
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = {"com.frank.repository"},
        entityManagerFactoryRef = "mydbEntityManager",
        transactionManagerRef = "mydbTransactionManager")
public class MydbDatasourceConfig {
    @Value("${frank.data.url}")
    private String jdbcUrl;

    @Value("${frank.data.password}")
    private String password;

    @Value("${frank.data.username}")
    private String username;

    @Value("${frank.data.pool-size}")
    private Integer poolSize;

    @Value("${frank.data.unique-name}")
    private String uniquename;


    @Primary
    @Bean(name = "mydbDataSource")
    @ConfigurationProperties(prefix = "frank.data")
    public DataSource mydbDataSource() {

        return DruidDataSourceBuilder.create().build();
    }

    @Primary
    @Bean(name = "mydbEntityManager")
    public LocalContainerEntityManagerFactoryBean clothoEntityManager(EntityManagerFactoryBuilder builder) throws Throwable {

        return builder.dataSource(mydbDataSource())
                .packages("com.frank.entity")
                .persistenceUnit(uniquename)
                .build();
    }

    @Primary
    @Bean(name = "mydbTransactionManager")
    public PlatformTransactionManager transactionManager(
            @Qualifier("mydbEntityManager") EntityManagerFactory
                    entityManagerFactory
    ) {
        return new JpaTransactionManager(entityManagerFactory);
    }
}
