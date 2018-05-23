package com.akami.clearview.service;

import com.akami.clearview.db.RequestSetDAO;

import javax.inject.Inject;

/**
 * Created by akamikang on 2017. 7. 8..
 */
//@Named
public class RequestService {
    //Request를 관리하는 서비스는 무슨일을 해야할까
    //1. 먼저 모든 request 정보가 담긴 객체를 관리해야합니다 RequestMetaSet

    @Inject
    private RequestSetDAO requestSetDAO;

    public String getCurrentDataTime() throws Exception {
        return requestSetDAO.getCurrentDateTime();
    }

}