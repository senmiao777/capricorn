package com.frank;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * Created by Administrator on 2017/4/26 0026.
 */
@Configuration
@EnableAspectJAutoProxy
@EnableAsync
@SpringBootApplication
@PropertySource({"classpath:dev/my.properties","classpath:dev/application.properties"})
public class Application {
	public static void main(String[] args) {
		SpringApplication.run(Application.class);
	}




	/*@Bean
	public FilterRegistrationBean filterRegistrationBean() {
		FilterRegistrationBean registrationBean = new FilterRegistrationBean();
		IPFilter ipFilter = new IPFilter();
		registrationBean.setFilter(ipFilter);
		List<String> urlPatterns = new ArrayList<String>();
		urlPatterns.add("/user/*");
		//urlPatterns.add("/frank");
		registrationBean.setUrlPatterns(urlPatterns);
		return registrationBean;
	}*/
}
