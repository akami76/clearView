package com.akami.clearview;

import com.akami.clearview.domain.STOMP;
import com.akami.clearview.listener.MEMListener;
import com.akami.clearview.listener.ReqListener;
import com.akami.clearview.listener.ResValListener;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;

import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.Session;

/**
 * Created by akamikang on 2017. 7. 5..
 */
@Named
public class ActiveMQSessionManager {


    @Autowired
    ActiveMQConnectionFactory connectionFactory;

    @Autowired
    Connection connection;

    @Autowired
    ResValListener resValListener;

    @Autowired
    ReqListener reqListener;


    //생성자 함수 호출 수 처음 실행되는 메소드 지정
    @PostConstruct
    public void init(){
        Session session;
        try {
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            connection.start();
            //Memory Listener 달기
            session.createConsumer(session.createTopic(STOMP.CV_MEM.getTopicName())).setMessageListener(new MEMListener());
            //Response Listener 달기
            //session.createConsumer(session.createTopic(STOMP.CV_RES_VAL.getTopicName())).setMessageListener(new ResValListener());
            session.createConsumer(session.createTopic(STOMP.CV_RES_VAL.getTopicName())).setMessageListener(resValListener);
            //Request Listener 달기
            session.createConsumer(session.createTopic(STOMP.CV_REQ.getTopicName())).setMessageListener(reqListener);

  //● cJson.toString() :{"_cv_req_id":"1499440098347365000C4FBE3E31A9674C4F3CF82E54B1FF862","_cv_req_s_time":"2017/07/08 00:08:18.347","userAgent":"Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_5) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/59.0.3071.115 Safari/537.36","sessionId":"C4FBE3E31A9674C4F3CF82E54B1FF862","userBodyParam":"{\"b\":\"eds\",\"aa\":\"sfsf\"}","_cv_agent_id":"a01","method":"GET","uri":"/helloMysql","clientIP":"0:0:0:0:0:0:0:1","userHeaderParam":"{\"cookie\":\"_cv_=1497845278899380000AB81CC9F379D91E67C7904EC9B56F33C; _cv_cookie=149901024074213900039174E895D8F3B394BBF4FC149B9C741; Idea-a745aec3=997b1e95-f0ac-4475-aafc-4624d4bb4a3f; JSESSIONID=C4FBE3E31A9674C4F3CF82E54B1FF862\",\"cache-control\":\"max-age=0\",\"_cv_req_id\":\"1499440098347365000C4FBE3E31A9674C4F3CF82E54B1FF862\",\"connection\":\"keep-alive\",\"accept-language\":\"ko-KR,ko;q=0.8,en-US;q=0.6,en;q=0.4\",\"host\":\"localhost:8080\",\"sessionId\":\"C4FBE3E31A9674C4F3CF82E54B1FF862\",\"accept\":\"text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8\",\"user-agent\":\"Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_5) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/59.0.3071.115 Safari/537.36\",\"accept-encoding\":\"gzip, deflate, br\",\"upgrade-insecure-requests\":\"1\"}"}





        } catch (JMSException e) {
            e.printStackTrace();
        }

    }

    @Bean
    public ResValListener resValListener(){
        return new ResValListener();
    }

    @Bean
    public ReqListener reqListener(){
        return new ReqListener();
    }




}
