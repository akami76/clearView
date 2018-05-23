package com.akami.clearview.listener;

import com.akami.clearview.domain.STOMP;
import org.jsoup.Jsoup;
import org.springframework.scheduling.annotation.Async;

import javax.inject.Named;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 * Created by akamikang on 2017. 6. 29..
 */


@Named
public class MEMListener implements MessageListener {
    @Override
    @Async
    public void onMessage(Message message) {

        try {
            TextMessage textMessage = (TextMessage) message;


            String url = "http://127.0.0.1:10000/send/" + STOMP.CV_MEM.getUri() + "?message=" + textMessage.getText();
            //System.out.println(url);
            //System.out.println(Jsoup.connect(url).get().outerHtml());
            Jsoup.connect(url).get().outerHtml();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

   /* Map<String, String> vars = new HashMap<String, String>();
vars.put("hotel", "42");
        vars.put("booking", "21");
        String result = restTemplate.getForObject("http://example.com/hotels/{hotel}/bookings/{booking}", String.class, vars);*/

