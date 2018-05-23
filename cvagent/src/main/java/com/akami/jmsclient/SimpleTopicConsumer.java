package com.akami.jmsclient;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.ExceptionListener;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;

public class SimpleTopicConsumer implements Runnable, ExceptionListener
{
    private final String brokerUrl;
    private final String topicName;

    public SimpleTopicConsumer(String brokerUrl, String topicName)
    {
        this.brokerUrl = brokerUrl;
        this.topicName = topicName;
    }

    public void run()
    {
        try
        {
            System.out.println("SimpleTopicConsumer, started on " + topicName);

            // Create a ConnectionFactory
            ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(brokerUrl);

            // Create a Connection
            Connection connection = connectionFactory.createConnection();
            connection.start();

            connection.setExceptionListener(this);

            // Create a Session
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

            // Create the destination (Topic or Queue)
            Destination destination = session.createTopic(topicName);

            // Create a MessageConsumer from the Session to the Topic or
            // Queue
            MessageConsumer consumer = session.createConsumer(destination);

           // long startTime = System.currentTimeMillis();

            while (true)
            {
                long now = System.currentTimeMillis();
                //if (now - startTime > lifetime)
                //{
                   // System.out.println("Time's up, exiting...");
                  //  break;
                //}

                // Wait for a message
                Message message = consumer.receive(100);

                if (message == null)
                    continue;

                if (message instanceof TextMessage)
                {
                    TextMessage textMessage = (TextMessage) message;
                    String text = textMessage.getText();
                    
//                    String msg[] = text.split("&");
//                    System.out.println("text : " + text);
//                    System.out.println("msg[] : " + msg.length);
//                    
//                    for(String index : msg){
//                    	System.out.println(index);
//                    }
//                    System.out.println("msg[] : " + msg[0]);
//                    long startTime = Long.parseLong(msg[0]);
//                    
//                    long elapseTime = now - startTime;
                    
                    
                    System.out.println("1 받았다. consumer - Received (text): " + text);
                }
                else
                {
                    System.out.println("2 받았다. consumer - Received: " + message);
                }
            }

            //consumer.close();
            //session.close();
            //connection.close();
        }
        catch (Exception e)
        {
            System.out.println("Caught: " + e);
            e.printStackTrace();
        }
    }

    public synchronized void onException(JMSException ex)
    {
        System.out.println("JMS Exception occured.  Shutting down client.");
    }
}
