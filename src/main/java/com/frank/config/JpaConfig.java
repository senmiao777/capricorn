package com.frank.config;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@ComponentScan
@Configuration
@Slf4j
//@EnableJpaRepositories(basePackages = "com.frank.repository")
@EnableTransactionManagement
public class JpaConfig {

  /* @Value("${spring.datasource.password}")
   private String password;

    @Value("${spring.datasource.url}")
    private String jdbcUrl;

    @Value("${spring.datasource.username}")
    private String username;



    @Bean
    @DependsOn(value = "dataSource")
    public EntityManagerFactory entityManagerFactory(DataSource dataSource) {
        LocalContainerEntityManagerFactoryBean entityManager = new LocalContainerEntityManagerFactoryBean();
        entityManager.setDataSource(SpringContextHolder.getBean("dataSource",DataSource.class));
      *//*  SpringContextHolder.getBean("videoInfoService",
                IVideoInfoService.class);*//*
      //  entityManager.setDataSource(dataSource);
        entityManager.setPackagesToScan("com.frank");
        entityManager.setPersistenceUnitName("dataSource");
        Properties properties = new Properties();
        properties.put("hibernate.jdbc.batch_size", 30);
        properties.put("hibernate.order_inserts", true);
        properties.put("hibernate.order_updates", true);
        entityManager.setJpaProperties(properties);
        entityManager.setJpaVendorAdapter(jpaVendorAdapter());
        entityManager.afterPropertiesSet();
        return entityManager.getObject();
    }

    @Bean
    public DataSource dataSource() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(jdbcUrl);
        config.setPassword(password);
        config.setUsername(username);
        config.setMaximumPoolSize(100);
        config.setMinimumIdle(20);
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        return new HikariDataSource(config);

    }


    private JpaVendorAdapter jpaVendorAdapter() {
        HibernateJpaVendorAdapter hibernateJpaVendorAdapter = new HibernateJpaVendorAdapter();
        hibernateJpaVendorAdapter.setShowSql(false);
        hibernateJpaVendorAdapter.setGenerateDdl(false);
        hibernateJpaVendorAdapter.setDatabase(Database.MYSQL);
        return hibernateJpaVendorAdapter;
    }


    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory);
        return transactionManager;
    }*/

}

