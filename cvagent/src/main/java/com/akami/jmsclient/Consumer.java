package com.akami.jmsclient;

import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.spring.ActiveMQConnectionFactory;


public class Consumer {
	public static Consumer CONSUMER;

	public Consumer() throws JMSException {
		ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
		connectionFactory.setBrokerURL("tcp://localhost:61616");

		Connection connection = connectionFactory.createConnection();
		connection.start();

		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		MessageConsumer consumer = session.createConsumer(session.createTopic("IntelliJ"));
		// MessageConsumer consumer =
		// session.createConsumer(session.createTopic("IntelliJ"));
		consumer.setMessageListener(new HelloMessageListener());
		//consumer.setMessageListener(new CVBroadcasting());

	}

	public static Consumer getInstance() {
		if (CONSUMER == null) {
			try {

				CONSUMER = new Consumer();
				System.out.println("Consumer Created! ");
			} catch (JMSException e) {
				System.out.println("Consumer 생성시 오류 발생 ");
				e.printStackTrace();
			}

		} else {
			System.out.println("Consumer Created! ");
		}
		return CONSUMER;
	}

	private static class HelloMessageListener implements MessageListener {

		public void onMessage(Message message) {
			System.out.println("onMessage 이벤트를 받았다.");
			TextMessage textMessage = (TextMessage) message;
			try {
				System.out.println("consumer가 받은 메세지다. " + Thread.currentThread().getName() + " received message: "
						+ textMessage.getText());
			} catch (JMSException e) {
				e.printStackTrace();
			}
		}

	}

	public static void main(String[] args) throws JMSException {
		System.out.println("consumer를 시작한다....");
		Consumer consumer = new Consumer();
	}

}
