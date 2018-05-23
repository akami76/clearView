package com.akami.clearview.listener;

import com.akami.clearview.domain.*;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.Async;

import javax.inject.Named;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 * Created by akamikang on 2017. 7. 7..
 */
@Named
public class ResValListener implements MessageListener {

    @Autowired
    RequestTrafficMap requestTrafficMap;
    @Autowired
    RequestMetaSetImpl requestMetaSet;

    @Autowired
    ResponseSetRepository responseSetRepository;

    @Override
    @Async
    public void onMessage(Message message) {

        try {
            TextMessage textMessage = (TextMessage) message;

            //RequestMetaSetImpl requestMetaSet = new RequestMetaSetImpl(new JSONObject(textMessage.getText()));

            JSONObject pointMark = new JSONObject(textMessage.getText());
            String cv_req_id =  (String)pointMark.get("cv_req_id");
            int cv_elaspe_time =(Integer) pointMark.get("cv_elaspe_time");
            String cv_res_end_time =(String)pointMark.get("cv_res_end_time");
            int status =(Integer)pointMark.get("status");
            int cnt = requestTrafficMap.requestComplete(cv_req_id);
            cv_res_end_time = cv_res_end_time.substring(0, cv_res_end_time.length()-4);
            System.out.println("=ResValListener==========================");
            System.out.println(textMessage.getText());
            System.out.println("cnt : "+cnt);
            System.out.println("===========================");





            pointMark = new JSONObject();
            pointMark.put("id", cv_req_id)
                    .put("elapseTime", cv_elaspe_time)
                    .put("time", cv_res_end_time)
                    .put("status", status);

            String url = "http://127.0.0.1:10000/send/" + STOMP.CV_RES_VAL.getUri() + "?message=" + pointMark.toString();
            Jsoup.connect(url).get().outerHtml();


            JSONObject resposeJson = new JSONObject(textMessage.getText());
            ResponseSet responseSet  = new ResponseSet();
            responseSet.setCvCookieId(resposeJson.getString("cv_cookie_id"));
            responseSet.setCvReqId(resposeJson.getString("cv_req_id"));
            responseSet.setCvElapseTime(resposeJson.getInt("cv_elaspe_time"));
            responseSet.setCvResEndTime(resposeJson.getString("cv_res_end_time"));
            responseSet.setStatus(resposeJson.getInt("status"));
            responseSet.setUserAgent(resposeJson.getString("userAgent"));

            responseSetRepository.save(responseSet);


            /*RequestSet requestSet = new RequestSet();
            requestSet.setCvReqId(resposeJson.getString("_cv_req_id"));
            requestSet.setStatus(resposeJson.getInt("status"));
            requestSet.setCvCookieId(resposeJson.getString("_cv_cookie"));
            //_cv_res_e_time
            requestSet.setCvElaspseTime(resposeJson.getLong("_cv_elaspe_time"));
            requestSet.setCvResETime(resposeJson.getString("_cv_res_e_time"));*/
            //requestSetRepository.save(requestSet);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Bean
    public RequestMetaSetImpl requestMetaSet(){
        return new RequestMetaSetImpl();

    }
}
