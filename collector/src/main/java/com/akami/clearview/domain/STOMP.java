package com.akami.clearview.domain;


/**
 * Created by akamikang on 2017. 6. 30..
 */
public enum STOMP {

    CV_CPU("CV_CPU", "cpu"),
    CV_MEM("CV_MEM","mem"),
    REQ_CNT("CV_REQ_CNT","reqCnt"),
    RES_CNT("CV_RES_CNT","resCnt"),
    THREAD_CNT("CV_THREAD_CNT", "threadCnt"),
    CV_RES_VAL("CV_RES_VAL", "socketPoint"),
    CV_REQ("CV_REQ", "cvReq");

    private String topicName ;
    private String uri;

    STOMP(String topicName, String uri){
        this.topicName = topicName;
        this.uri = uri;
    }

    public String getTopicName(){
        return this.topicName;
    }

    public String getUri(){
        return  this.uri;
    }


}
        /*
        send/cpu?{cpu:90}
        /send/mem?{mem:90}
        /send/reqCnt?{cnt:10}
        /send/resCnt?{cnt:30}
        /send/socketPoint?{id:'aaaa', time:'24hhmmss', elapseTime:2000}
        */