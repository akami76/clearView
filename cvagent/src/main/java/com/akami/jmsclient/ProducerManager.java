package com.akami.jmsclient;

import com.akami.com.TOPIC;
import org.apache.activemq.spring.ActiveMQConnectionFactory;

import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.Session;

import java.util.HashMap;

import static java.lang.System.*;

/**
 * Created by akamikang on 2017. 6. 29..
 */
public class ProducerManager {

    public static Producer[] PRODUCER_MANAGER_POOL ;
    public static Connection CONNECTION;
    public static Session SESSION;
   // public static HashMap<String, Producer> PRODUCER_MAP ;



    public ProducerManager() {
        // Create a ConnectionFactory
        System.out.println("producer가 activemq서버와 연결을 한다.");
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();

        try {
            connectionFactory.setBrokerURL("tcp://localhost:61616");
            CONNECTION = connectionFactory.createConnection();
            CONNECTION.start();
            SESSION = CONNECTION.createSession(false, Session.AUTO_ACKNOWLEDGE);

            System.out.println("ProducerManager 연결되었다..");
            PRODUCER_MANAGER_POOL = new Producer[]{
                    new Producer(TOPIC.CV_REQ.getTopicName()),
                    new Producer(TOPIC.CV_MEM.getTopicName()),
                    new Producer(TOPIC.CV_VAL.getTopicName()),
                    new Producer(TOPIC.CV_RES_VAL.getTopicName()),
                    new Producer(TOPIC.CV_RES.getTopicName()),
                    new Producer(TOPIC.CV_CPU.getTopicName()),
                    new Producer(TOPIC.CV_THREAD_CNT.getTopicName()),
                    new Producer(TOPIC.CV_CALL_TREE.getTopicName())
            };

            for(TOPIC topic : TOPIC.values()){
                // PRODUCER_MAP.put(topic.getTopicName(), new Producer( topic.getTopicName()));

                System.out.println(topic.getTopicName()+": Consumer가 생성되었다.["+topic.getTopicName()+"]");
            }

            System.out.println(PRODUCER_MANAGER_POOL.length + " 개의 producer가 생성되었다.");
        } catch (Exception e) {
            System.out.println("ActiveMQ not Connected!~~~");
            e.printStackTrace();
        }

    }

    public static void main(String[] args) throws JMSException {
        ProducerManager a = new ProducerManager();

      // com.akami.jmsclient.Producer cvVAL = com.akami.jmsclient.ProducerManager.PRODUCER_MAP.get(com.akami.com.TOPIC.CV_VAL.getTopicName());
       // cvVAL.produceMessage("HI");

    }


}

