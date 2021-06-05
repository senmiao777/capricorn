package com.frank.config;

import com.alibaba.druid.filter.logging.Slf4jLogFilter;
import com.alibaba.druid.filter.stat.StatFilter;
import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
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
import java.sql.SQLException;

@Slf4j
@Configuration
@EntityScan(basePackages = {"com.frank.entity"})
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = {"com.frank.repository"},
        entityManagerFactoryRef = "mydbEntityManager",
        transactionManagerRef = "mydbTransactionManager")
public class MydbDatasourceConfig {
    @Value("${spring.datasource.druid.frank.url}")
    private String jdbcUrl;

    @Value("${spring.datasource.druid.frank.password}")
    private String password;

    @Value("${spring.datasource.druid.frank.username}")
    private String username;

    @Value("${spring.datasource.druid.frank.pool-size}")
    private Integer poolSize;

    @Value("${spring.datasource.druid.frank.unique-name}")
    private String uniquename;

    @Bean(name = "statFilter")
    public StatFilter statFilter(){
        StatFilter statFilter = new StatFilter();
        statFilter.setMergeSql(true);
        statFilter.setLogSlowSql(true);
        statFilter.setSlowSqlMillis(1000L);

        return statFilter;


    }

    @Bean(name = "slf4jFilter")
    public Slf4jLogFilter slf4jFilter(){
        Slf4jLogFilter slf4jFilter = new Slf4jLogFilter();
        slf4jFilter.setResultSetLogEnabled(true);
        slf4jFilter.setDataSourceLogEnabled(true);
        slf4jFilter.setStatementLogEnabled(true);
        return slf4jFilter;


    }

    @Bean
    public MyStatLog myStatLog(){
        return new MyStatLog();
    }

    @Primary
    @Bean(name = "mydbDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.druid.frank")
    public DataSource mydbDataSource() {
        DruidDataSource druidDataSource = DruidDataSourceBuilder.create().build();

        try {
            druidDataSource.setFilters("wall,log4j");
            druidDataSource.setProxyFilters(Lists.newArrayList(statFilter(),slf4jFilter()));
            druidDataSource.setUseGlobalDataSourceStat(true);
            druidDataSource.setDbType("mysql");
            druidDataSource.setInitialSize(1);
            druidDataSource.setMinIdle(1);
            druidDataSource.setMaxActive(20);
            druidDataSource.setMaxWait(60000L);
            druidDataSource.setTimeBetweenEvictionRunsMillis(60000L);
            druidDataSource.setValidationQuery("SELECT 'x'");
            druidDataSource.setTestWhileIdle(true);
            druidDataSource.setTestOnBorrow(false);
            druidDataSource.setTestOnReturn(false);
            druidDataSource.setPoolPreparedStatements(false);
            druidDataSource.setMaxPoolPreparedStatementPerConnectionSize(20);
          //  druidDataSource.setStatLogger(myStatLog());

        } catch (SQLException e) {
            e.printStackTrace();
        }
        log.info("druidDataSource init finish={}",druidDataSource);

        return druidDataSource;
    }

    @Primary
    @Bean(name = "mydbEntityManager")
    public LocalContainerEntityManagerFactoryBean myEntityManager(EntityManagerFactoryBuilder builder) throws Throwable {
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
