package com.akami.clearview.config;

import com.akami.clearview.domain.RequestMetaSetImpl;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.jms.Connection;
import javax.jms.JMSException;

/**
 * Created by akamikang on 2017. 6. 29..
 */
//Configuration 을 붙여서 이 클래스가 java Config 용 클래스 임을 컴파일리에게 알린다.
@Configuration
public class ConsumerConfig {

/*    @Bean
    public ClientHttpRequestFactory clientHttpRequestFactory() {
        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
        factory.setReadTimeout(2000);// milli-seconds
        factory.setConnectTimeout(2000);// milli-seconds
        return factory;
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate(clientHttpRequestFactory());
    }*/

    //DI 컨테이너가 관리할 Bean을 생성하는 메소드에는 @Bean annotation을 붙인다
    @Bean
    public ActiveMQConnectionFactory connectionFactory() {
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
        connectionFactory.setBrokerURL("tcp://localhost:61616");
        return connectionFactory;
    }

    //DI 컨테이너가 관리할 Bean을 생성하는 메소드에는 @Bean annotation을 붙인다
    @Bean
    public Connection connection() {
        try {
            return connectionFactory().createConnection();
        } catch (JMSException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Bean
    public RequestMetaSetImpl requestMetaSet(){
        return new RequestMetaSetImpl();

    }

}


