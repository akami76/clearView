package com.akami.clearview.domain;

import org.json.JSONException;
import org.json.JSONObject;
//import javax.inject.Inject;

//import org.apache.ibatis.session.SqlSession;

/**
 * Created by akamikang on 2017. 7. 3..
 */
//@Repository
public class RequestMetaSetImpl {

 /*"_cv_req_id":"14990882589298080008A8CC7864000204F96CB93DF54C7D9E2",
 "_cv_req_s_time":"2017/07/03 22:24:18.929",
 "userAgent":"Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_5) AppleWebKit/603.2.4 (KHTML, like Gecko) Version/10.1.1 Safari/603.2.4",
 "sessionId":"8A8CC7864000204F96CB93DF54C7D9E2",
 "userBodyParam":"{\"akami\":\"asdsf\"}",
 "_cv_agent_id":"a01",
 "method":"GET",
 "uri":"/helloMysql",
 "clientIP":"0:0:0:0:0:0:0:1",*/
    //"userHeaderParam":"{\"cookie\":\"JSESSIONID=8A8CC7864000204F96CB93DF54C7D9E2; _cv_cookie=1499000153548653000F5D408D328D3CCBA97685F4F44E9AFE0; _cv_=149803249296827700085A04BDF68166E032CD70FF0E4021867\",\"_cv_req_id\":\"14990882589298080008A8CC7864000204F96CB93DF54C7D9E2\",\"connection\":\"keep-alive\",\"accept-language\":\"ko-kr\",\"host\":\"localhost:8080\",\"sessionId\":\"8A8CC7864000204F96CB93DF54C7D9E2\",\"accept\":\"text/html,application/xhtml+xml,application/xml;q=0.9,**/*//*;q=0.8\",\"user-agent\":\"Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_5) AppleWebKit/603.2.4 (KHTML, like Gecko) Version/10.1.1 Safari/603.2.4\",\"accept-encoding\":\"gzip, deflate\",\"upgrade-insecure-requests\":\"1\"}"}

    private String cvReqId;
    private String cvReqSTime;
    private String userAgent;
    private String sessionId;
    private JSONObject userBodyPram;
    private String cvAgentId;
    private String method;
    private String uri;
    private String clientIP;
    private JSONObject userHeaderParam;


    public RequestMetaSetImpl(JSONObject requestSet) throws JSONException {
        this.cvReqId = requestSet.getString("cv_req_id");
        this.cvReqSTime = requestSet.getString("cv_req_start_time");
        this.userAgent = requestSet.getString("userAgent");
        this.sessionId = requestSet.getString("sessionId");
        this.userBodyPram = new JSONObject(requestSet.getString("userBodyParam"));
        this.cvAgentId = requestSet.getString("cv_agent_id");
        this.method = requestSet.getString("method");
        this.uri = requestSet.getString("uri");
        this.clientIP = requestSet.getString("clientIP");
        this.userHeaderParam = new JSONObject(requestSet.getString("userHeaderParam"));

    }

    public RequestMetaSetImpl() {

    }


    public String getCvReqId() {
        return cvReqId;
    }

    public void setCvReqId(String cvReqId) {
        this.cvReqId = cvReqId;
    }

    public String getCvReqSTime() {
        return cvReqSTime;
    }

    public void setCvReqSTime(String cvReqSTime) {
        this.cvReqSTime = cvReqSTime;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public JSONObject getUserBodyPram() {
        return userBodyPram;
    }

    public void setUserBodyPram(JSONObject userBodyPram) {
        this.userBodyPram = userBodyPram;
    }

    public String getCvAgentId() {
        return cvAgentId;
    }

    public void setCvAgentId(String cvAgentId) {
        this.cvAgentId = cvAgentId;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getClientIP() {
        return clientIP;
    }

    public void setClientIP(String clientIP) {
        this.clientIP = clientIP;
    }

    public JSONObject getUserHeaderParam() {
        return userHeaderParam;
    }

    public void setUserHeaderParam(JSONObject userHeaderParam) {
        this.userHeaderParam = userHeaderParam;
    }

    public RequestMetaSetImpl setRequestMetaSet(JSONObject requestMetaSet) throws JSONException {

        return new RequestMetaSetImpl(requestMetaSet);
    }
}
