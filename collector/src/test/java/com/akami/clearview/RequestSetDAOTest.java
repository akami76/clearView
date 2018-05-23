package com.akami.clearview;

import com.akami.clearview.db.RequestSetDAO;
import com.akami.clearview.domain.RequestSet;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by akamikang on 2017. 7. 11..
 */
public class RequestSetDAOTest extends App2ApplicationDataSourceTests{
    @Autowired
    private RequestSetDAO requestSetDAO;

    @Test
    public void testInsert() throws Exception{
        RequestSet requestSet = new RequestSet();
        requestSet.setCvReqId("000001");
        requestSetDAO.getCurrentDateTime();
    }
}