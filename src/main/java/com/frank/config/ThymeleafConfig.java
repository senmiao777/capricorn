package com.frank.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ViewResolver;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.thymeleaf.templateresolver.TemplateResolver;

/**
 * @author frank
 * @version 1.0
 * @date 2018/7/24 0024 下午 9:42
 */
@Configuration
public class ThymeleafConfig {
    /**
     * 设置视图解析器
     * @param templateEngine
     * @return
     */
    @Bean
    public ViewResolver viewResolver(SpringTemplateEngine templateEngine){
        ThymeleafViewResolver resolver = new ThymeleafViewResolver();
        resolver.setTemplateEngine(templateEngine);
        return resolver;
    }

    /**
     * 设置模板引擎
     * @param templateResolver
     * @return
     */
    @Bean
    public TemplateEngine templateEngine(TemplateResolver templateResolver){
        SpringTemplateEngine engine = new SpringTemplateEngine();
        engine.setTemplateResolver(templateResolver);
        return engine;
    }

    /**
     * 模板解析引擎
     * @return
     */
//    @Bean
//    public TemplateResolver templateResolver(){
//        TemplateResolver resolver = new SpringResourceTemplateResolver();
//        //设置地址前缀
//        resolver.setPrefix("/static/template/");
//        //设置后缀
//        resolver.setSuffix(".html");
//        //设置不缓存
//        resolver.setCacheable(false);
//        resolver.setTemplateMode("HTML5");
//        return resolver;
//
//    }

     //模板解析器
    @Bean
    public ClassLoaderTemplateResolver emailTemplateResolver()
    {
        ClassLoaderTemplateResolver resolver = new ClassLoaderTemplateResolver();
        resolver.setPrefix("static/template/");
        resolver.setTemplateMode("HTML5");
        resolver.setOrder(1);
        resolver.setCharacterEncoding("UTF-8");
        return resolver;
    }
}
