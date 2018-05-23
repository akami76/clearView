package com.akami.clearview.db;

import com.akami.clearview.domain.RequestSet;
import com.akami.clearview.domain.ResponseSet;

import javax.inject.Named;

/**
 * Created by akamikang on 2017. 7. 13..
 */
@Named
public interface RequestSetDAO {

    String getCurrentDateTime();

    //Requset 정보를 저장한다
    void saveRequestSet(RequestSet requestSet);

    //Response 결과를 저장한다
    void saveResponseSet(ResponseSet responseSet);
}
