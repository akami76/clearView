package com.akami.clearview.websocket;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class Producer {
	
	 private static final SimpleDateFormat dateFormatter = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
	
	 @Autowired
	 private SimpMessagingTemplate template;

	 @Async
	 public void sendMessageTo(String topic, String message) {
		 	StringBuilder builder = new StringBuilder();
		 	/*builder.append("[");
		 	builder.append(dateFormatter.format(new Date()));
		 	builder.append("] ");*/
		 	builder.append(message);
		 	
	        this.template.convertAndSend("/topic/" + topic, builder.toString());
	 }
}

