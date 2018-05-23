package com.akami.server;

import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import com.akami.jmsclient.Consumer;

@ServerEndpoint("/cvbroadcasting")
@Controller
public class CVBroadcasting {
	private static Set<Session> clients = Collections.synchronizedSet(new HashSet<Session>());
	public static Consumer consumer;

	public CVBroadcasting() {
		System.out.println("--------------------------------------------------");
		System.out.println("Consumer start");
		System.out.println("--------------------------------------------------");
		
		consumer = Consumer.getInstance();

	}

	@OnMessage
	public void onMessage(String message, Session session) throws IOException {
		System.out.println(message);

		synchronized (clients) {
			for (Session client : clients) {
				// if(!client.equals(session)) {
				System.out.println("1.send from client : " + message);
				client.getBasicRemote().sendText(message);
				// }
			}
		}
	}

	@OnOpen
	public void onOpen(Session session) {
		System.out.println(session);
		clients.add(session);

		// int i =0;
		// while(true){
		// try {
		//
		// session.getBasicRemote().sendText(i+"");
		// Thread.sleep(1000);
		// } catch (IOException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// } catch (InterruptedException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		// }

	}

	@OnClose
	public void onClose(Session session) {
		clients.remove(session);
	}

	
	
}
