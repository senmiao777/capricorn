package com.frank.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;

/**
 * @author frank
 * @version 1.0
 * @date 2017/12/10 0010 下午 12:15
 */
@Slf4j
public class InitConfig implements CommandLineRunner {
    @Override
    public void run(String... strings) throws Exception {
        log.info("init begin ...");
    }
}
