package com;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import javax.jms.JMSException;

@EnableConfigurationProperties
@SpringBootApplication
public class WebsokcetApplication {

	public static void main(String[] args) throws JMSException {

		SpringApplication.run(WebsokcetApplication.class, args);

	}

	/*@Bean
	public InternalResourceViewResolver setupViewResolver(){
		InternalResourceViewResolver resolver = new InternalResourceViewResolver();
		resolver.setPrefix("/WEB-INF/jsp/");
		resolver.setSuffix(".jsp");
		return resolver;

	}*/
}
   