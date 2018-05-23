package com.akami.context;

public class ServletTraceContext {
    private String serviceName;
    private int servicHash;
    private long startTime;
    private String orgThreadName;

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public int getServicHash() {
        return servicHash;
    }

    public void setServicHash(int servicHash) {
        this.servicHash = servicHash;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public String getOrgThreadName() {
        return orgThreadName;
    }

    public void setOrgThreadName(String orgThreadName) {
        this.orgThreadName = orgThreadName;
    }
}

