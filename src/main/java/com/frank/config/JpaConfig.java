package com.frank.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 *
 */
@ComponentScan
@Configuration
@Slf4j
@EnableTransactionManagement
@EnableJpaAuditing
public class JpaConfig {


}

