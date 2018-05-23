package com.akami.clearview.listener;

import com.akami.clearview.domain.RequestMetaSetImpl;
import com.akami.clearview.domain.RequestSet;
import com.akami.clearview.domain.RequestSetRepository;
import com.akami.clearview.domain.RequestTrafficMap;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

import javax.inject.Named;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by akamikang on 2017. 7. 8..
 */

@Named
public class ReqListener implements MessageListener {

    @Autowired
    RequestTrafficMap requestTrafficMap;
    @Autowired
    RequestMetaSetImpl requestMetaSet;

    @Autowired
    RequestSetRepository requestSetRepository;

    /*@Autowired
    RequestService requestService;
*/

    //@Autowired
    //private RequestSetDAO requestSetDAO;


    @Override
    public void onMessage(Message message) {

        try {
            TextMessage textMessage = (TextMessage) message;
            JSONObject reqposeJson = new JSONObject(textMessage.getText());


            // ● responseJson.toString() :{"_cv_req_id":"14999103518423340007A63ACE86F8BF12579A4533808B3B5BD",
            // "sessionId":"7A63ACE86F8BF12579A4533808B3B5BD",
            // "userAgent":"Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_5) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/59.0.3071.115 Safari/537.36","status":404,"_cv_cookie":"149901024074213900039174E895D8F3B394BBF4FC149B9C741","_cv_elaspe_time":8,"_cv_res_e_time":"2017/07/13 10:45:51.850"}
            requestMetaSet = requestMetaSet().setRequestMetaSet(reqposeJson);
            int cnt = requestTrafficMap.addRequest(requestMetaSet);


            RequestSet requestSet = new RequestSet();
            //requestSet.setCvCookieId(reqposeJson.getString("_cv_cookie"));
            requestSet.setCvReqId(reqposeJson.getString("cv_req_id"));
            requestSet.setCvAgentId(reqposeJson.getString("cv_agent_id"));
            requestSet.setClientIP(reqposeJson.getString("clientIP"));
            String from = reqposeJson.getString("cv_req_start_time");
            SimpleDateFormat transFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss.SSS");
            Date cvReqSTime = transFormat.parse(from);
            requestSet.setCvReqStartTime(cvReqSTime);
            requestSet.setMethod(reqposeJson.getString("method"));
            requestSet.setSessionId(reqposeJson.getString("sessionId"));
            requestSet.setUri(reqposeJson.getString("uri"));
            requestSet.setUserAgent(reqposeJson.getString("userAgent"));

           // System.out.println("requestMetaSet : "+requestMetaSet);
           // System.out.println("requestMetaSet : "+requestMetaSet.getCvReqId());
           // System.out.println("cnt : "+ cnt);
            System.out.println(requestSet.getCvReqId()+" saved.] ==========================="+requestSetRepository.save(requestSet));;

           // RequestSet requestSet = new RequestSet();
            //requestSet.setCvReqId(resposeJson.getString("CvReqId"));
            //requestSet.setCvAgentId(resposeJson.getString("CvReqId"));
            //requestSet.setCvCookieId(resposeJson.getString("CvReqId"));

            //requestSetRepository.save

            //System.out.println("requestService.getCurrentDataTime() : =================>"+requestService.getCurrentDataTime());

//            System.out.println("requestSetDAO.getCurrentDateTime() : "+requestSetDAO.getCurrentDateTime());

            //requestSetDAO.getCurrentDateTime();



            //Connection connection = dataSource.getConnection();
            //System.out.println(connection.getCatalog());

 //           RequestMetaSetImpl requestMetaSet = new RequestMetaSetImpl(new JSONObject(textMessage.getText()));
 //           System.out.println("requestMetaSet:"+requestMetaSet);
  //          System.out.println("requestMetaSet:"+requestMetaSet.getCvReqId());
//            int cnt = requestTrafficMap.addRequest(requestMetaSet);
  //          System.out.println("cnt : "+ cnt);

            //connection.

           // String url = "http://127.0.0.1:10000/send/" + STOMP.CV_MEM.getUri() + "?message=" + textMessage.getText();
           // Jsoup.connect(url).get().outerHtml();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Bean
    public RequestMetaSetImpl requestMetaSet(){
        return new RequestMetaSetImpl();

    }

   /* @Bean
    public RequestSetDAO requestSetDAO(){
        return new RequestSetDAO();
    }*/
    /*@Bean
    public RequestService requestService(){
        return new RequestService();

    }*/



}
