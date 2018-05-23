package com.akami.clearview.websocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WebsockerController {
	@Autowired
	Producer producer;
	
	@RequestMapping("/send/{topic}")
	public String sender(@PathVariable String topic, @RequestParam String message) {

		//System.out.println("topic : "+topic);
		producer.sendMessageTo(topic, message);
		return "success";
	}
	
}