package com.akami.clearview.domain;

import lombok.Data;

import javax.inject.Named;
import javax.persistence.*;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by akamikang on 2017. 7. 13..
 */


/*CREATE TABLE `response_set` (
        `cv_req_id` varchar(100) NOT NULL,
        `cv_cookie_id` varchar(100) DEFAULT NULL,
        `status` int(4) DEFAULT null,
        `cv_elapse_time` timestamp NULL DEFAULT NULL,
        `cv_res_end_time` timestamp NULL DEFAULT NULL,
        `user_agent` varchar(1000) DEFAULT NULL,
        PRIMARY KEY (`cv_req_id`)
        )*/



@Entity
@Named
@Data
public class ResponseSet implements Serializable {
    @Id
    private String cvReqId;
    @Column(nullable=true, length = 100)
    private String cvCookieId;
    @Column(nullable=true, length = 100)
    private int status;
    @Column(nullable=true, length = 3)
    private int cvElapseTime;
    @Column(nullable=true)
    private Date cvResEndTime;
    @Column(nullable=true, length = 500)
    private String userAgent;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "regDate", insertable=false)
    private Date regDate;

    public void setCvResEndTime(String cvResEndTime) {
        SimpleDateFormat transFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss.SSS");
        Date cvReqSTimed = null;
        try {
            cvReqSTimed = transFormat.parse(cvResEndTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        this.cvResEndTime = cvReqSTimed;
    }
}
