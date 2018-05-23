package com.akami.jmsclient;

import javax.jms.*;

import static com.akami.jmsclient.ProducerManager.SESSION;


public  class Producer {

	private MessageProducer Mproducer;
	private String topicNm;


	public Producer( String topicNm) {
		Destination destination = null;
		try {
			destination = SESSION.createTopic(topicNm);
			Mproducer = SESSION.createProducer(destination);
			Mproducer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
		} catch (JMSException e) {
			System.out.println("ActiveMQ not Connected.");
			e.printStackTrace();
		}


		// Clean up
		// connection.close();
	}


	/*public static Producer getInstance( String topicNm) {
		if (PRODUCER == null) {
			try {
				PRODUCER = new Producer(topicNm);
                System.out.println("cvAgent.topicNm : "+topicNm);
            } catch (JMSException e) {
				System.out.println("producer 생성시 오류 발생 ");
				e.printStackTrace();
			}
		}
		return PRODUCER;
	}*/

	public void produceMessage(String msg) {
		try {
			//System.out.println("1   producer가 메세지를 보낸다.");
			// Create a messages

			TextMessage message = SESSION.createTextMessage(msg);

			// Tell the producer to send the message
			//System.out.println("Producer Sent.1");
			Mproducer.send(message);
			//System.out.println("[cvAgent]Producer Sent:"+message.toString());
		} catch (Exception e) {
			System.out.println("메세지 전송 오류 : " + e);
			e.printStackTrace();
		}
	}

	public void setTopicNm(String topicNm){
		this.topicNm = topicNm;
	}

	public String getTopicNm(){
		return this.topicNm;
	}

}
