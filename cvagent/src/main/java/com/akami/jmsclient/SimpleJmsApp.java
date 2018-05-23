package com.akami.jmsclient;

public class SimpleJmsApp {
	private static final String BROKER_URL = "tcp://localhost:61616?jms.prefetchPolicy.all=1000";
	private static final String TOPICS = "test_topic";

	public static void main(String[] args) throws Exception {

		
		System.out.println("1. starting producers...");
		SimpleTopicProducer producer = new SimpleTopicProducer(BROKER_URL, TOPICS);
		thread(producer, true);
		
		System.out.println("2. Now starting consumers...");
		SimpleTopicConsumer consumer = new SimpleTopicConsumer(BROKER_URL, TOPICS);
		thread(consumer, false);
		

	}

	public static void thread(Runnable runnable, boolean daemon) {
		Thread brokerThread = new Thread(runnable);
		brokerThread.setDaemon(daemon);
		brokerThread.start();
	}
}
