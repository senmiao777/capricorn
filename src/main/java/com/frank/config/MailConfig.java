package com.frank.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.MailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

/**
 * @author frank
 * @version 1.0
 * @date 2018/7/22 0022 下午 1:48
 */
@Configuration
public class MailConfig {
    @Bean
    public MailSender mailSender(){
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
        return javaMailSender;
    }
}
