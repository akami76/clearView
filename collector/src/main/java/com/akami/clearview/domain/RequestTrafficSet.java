package com.akami.clearview.domain;

/**
 * Created by akamikang on 2017. 7. 3..
 */
public interface RequestTrafficSet {
    public int getInitTrafficStatus();
    public int getWarningTrafficStatus();
    public int getFatalTrafficStatus();

    public int addRequest(RequestMetaSetImpl requestMetaSetImpl);
}
