package com.akami.clearview.domain;

import lombok.Data;

import java.sql.Timestamp;
import java.util.Date;

/**
 * Created by akamikang on 2017. 7. 17..
 */
@Data
public class PointMark {
    private String cvReqId;
    private String cvAgentId;
    private String cvCookieId;
    private String sessionId;
    private String clientIp;
    private long cvElapseTime;
    private String cvReqStartTime;
    private String cvResEndTime;
    private String method;
    private String uri;
    private int status;
    private String userAgent;
}
