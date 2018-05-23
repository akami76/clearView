package com.akami.clearview.domain;

import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by akamikang on 2017. 7. 3..
 */
@Component
public  class RequestTrafficMap implements RequestTrafficSet{

    public  Map<String, RequestMetaSetImpl> requestMetaSetMap = Collections.synchronizedMap(new HashMap<String, RequestMetaSetImpl>());

    private long birthDay ;

    @Override
    public int getInitTrafficStatus() {
        System.out.println("getInitTrafficStatus birthDay : "+birthDay);


        return requestMetaSetMap.size();
    }

    @Override
    public int getWarningTrafficStatus() {
        return 0;
    }

    @Override
    public int getFatalTrafficStatus() {
        return 0;
    }

    @Override
    public int addRequest(RequestMetaSetImpl requestMetaSetImpl) {
        System.out.println("!!!!!addRequest birthDay : "+birthDay);
        requestMetaSetMap.put(requestMetaSetImpl.getCvReqId(), requestMetaSetImpl);

        return getInitTrafficStatus();
    }

    public int requestComplete(String requestId){

         requestMetaSetMap.remove(requestId);

        return requestMetaSetMap.size();
    }


    public RequestTrafficMap(){
        birthDay = System.currentTimeMillis();
        System.out.println("birthDay : "+birthDay);
    }

}
