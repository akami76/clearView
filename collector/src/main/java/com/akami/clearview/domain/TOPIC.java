package com.akami.clearview.domain;

/**
 * Created by akamikang on 2017. 6. 30..
 */
public enum TOPIC {

    CV_REQ(0, "CV_REQ"),
    CV_MEM(1, "CV_MEM"),
    CV_VAL(2, "CV_VAL"),
    CV_RES_VAL(3, "CV_RES_VAL"),
    CV_RES(4, "CV_RES"),
    CV_CPU(5, "CV_CPU"),
    CV_THREAD_CNT(6, "CV_THREAD_CNT");

    private String topicName ;
    private int index;

    TOPIC(int index, String topicName){
        this.index = index;
        this.topicName = topicName;
    }

    public String getTopicName(){
        return this.topicName;
    }

    public int getIndex(){
        return this.index;
    }

}
        /*
        send/cpu?{cpu:90}
        /send/mem?{mem:90}
        /send/reqCnt?{cnt:10}
        /send/resCnt?{cnt:30}
        /send/socketPoint?{id:'aaaa', time:'24hhmmss', elapseTime:2000}
        */