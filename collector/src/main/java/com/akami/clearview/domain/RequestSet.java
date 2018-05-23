package com.akami.clearview.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * Created by akamikang on 2017. 7. 11..
 */

//{"_cv_req_id":"14999103809834930007A63ACE86F8BF12579A4533808B3B5BD",
// "_cv_req_s_time":"2017/07/13 10:46:20.983",
// "userAgent":"Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_5) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/59.0.3071.115 Safari/537.36",
// "sessionId":"7A63ACE86F8BF12579A4533808B3B5BD",
// "userBodyParam":"{\"b\":\"eds\",\"aa\":\"sfsf\"}",
// "_cv_agent_id":"a01",
// "method":"GET","uri":"/helloMysql","clientIP":"0:0:0:0:0:0:0:1",
// "userHeaderParam":"{\"cookie\":\"_cv_=1497845278899380000AB81CC9F379D91E67C7904EC9B56F33C; _cv_cookie=149901024074213900039174E895D8F3B394BBF4FC149B9C741; Idea-a745aec3=997b1e95-f0ac-4475-aafc-4624d4bb4a3f; JSESSIONID=7A63ACE86F8BF12579A4533808B3B5BD\",\"cache-control\":\"max-age=0\",\"_cv_req_id\":\"14999103809834930007A63ACE86F8BF12579A4533808B3B5BD\",\"connection\":\"keep-alive\",\"accept-language\":\"ko-KR,ko;q=0.8,en-US;q=0.6,en;q=0.4\",\"host\":\"localhost:8080\",\"sessionId\":\"7A63ACE86F8BF12579A4533808B3B5BD\",\"accept\":\"text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8\",\"user-agent\":\"Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_5) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/59.0.3071.115 Safari/537.36\",\"accept-encoding\":\"gzip, deflate, br\",\"upgrade-insecure-requests\":\"1\"}"}

/*CREATE TABLE `request_set` (
        `cv_req_id` varchar(100) NOT NULL,
        `cv_cookie_id` varchar(100) DEFAULT NULL,
        `cv_agent_id` varchar(20) DEFAULT NULL,
        `session_id` varchar(100) DEFAULT NULL,
        `method` varchar(50) DEFAULT NULL,
        `clien_ip` varchar(50) DEFAULT NULL,
        `uri` varchar(1000) DEFAULT NULL,
        `method` varchar(20) DEFAULT NULL,
        `cv_req_start_time` timestamp NULL DEFAULT NULL,
        `user_agent` varchar(1000) DEFAULT NULL,
        PRIMARY KEY (`cv_req_id`)
        )*/
@Entity
public class RequestSet implements Serializable {
    private static final long serialVersionUID = -4414900603554357580L;
    @Id
    private String cvReqId;
    @Column(nullable=true, length = 100)
    private String cvCookieId;
    @Column(nullable=true, length = 20)
    private String cvAgentId;
    @Column(nullable=true, length = 100)
    private String sessionId;
    @Column(nullable=true, length = 100)
    private String uri;
    @Column(nullable=true, length = 10)
    private String method;
    @Column(nullable=true)
    private Date cvReqStartTime;
    @Column(nullable=true, length = 500)
    private String userAgent;
    private String clientIP;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "regDate", insertable=false)
    private Date regDate;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getCvReqId() {
        return cvReqId;
    }

    public void setCvReqId(String cvReqId) {
        this.cvReqId = cvReqId;
    }

    public String getCvCookieId() {
        return cvCookieId;
    }

    public void setCvCookieId(String cvCookieId) {
        this.cvCookieId = cvCookieId;
    }

    public String getCvAgentId() {
        return cvAgentId;
    }

    public void setCvAgentId(String cvAgentId) {
        this.cvAgentId = cvAgentId;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public Date getCvReqStartTime() {
        return cvReqStartTime;
    }

    public void setCvReqStartTime(String cvReqStartTime) {
        SimpleDateFormat transFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss.SSS");
        Date cvReqSTimed = null;
        try {
            cvReqSTimed = transFormat.parse(cvReqStartTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        this.cvReqStartTime = cvReqSTimed;
    }

    public void setCvReqStartTime(Date cvReqStartTime) {
        this.cvReqStartTime = cvReqStartTime;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }


    public String getClientIP() {
        return clientIP;
    }

    public void setClientIP(String clientIP) {
        this.clientIP = clientIP;
    }

    public Date getRegDate() {
        return regDate;
    }

    public void setRegDate(Date regDate) {
        this.regDate = regDate;
    }

    @PrePersist
    protected void onCreate() {
        if (regDate == null) { regDate = new Date(); }
    }

    @Override
    public String toString() {
        return "RequestSet{" +
                "cvReqId='" + cvReqId + '\'' +
                ", cvCookieId='" + cvCookieId + '\'' +
                ", cvAgentId='" + cvAgentId + '\'' +
                ", sessionId='" + sessionId + '\'' +
                ", uri='" + uri + '\'' +
                ", method='" + method + '\'' +
                ", cvReqStartTime=" + cvReqStartTime +
                ", userAgent='" + userAgent + '\'' +
                ", clientIP='" + clientIP + '\'' +
                ", regDate=" + regDate +
                '}';
    }
}
