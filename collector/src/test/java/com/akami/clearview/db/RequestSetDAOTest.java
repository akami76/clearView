package com.akami.clearview.db;

import com.WebsokcetApplication;
import com.akami.clearview.domain.PointMark;
import com.akami.clearview.service.PointMarkBiz;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.inject.Inject;
import java.util.List;

/**
 * Created by akamikang on 2017. 7. 13..
 */
@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = WebsokcetApplication.class)
@WebAppConfiguration
public class RequestSetDAOTest {

    @Inject
    private RequestSetDAO requestSetDAO;

    @Inject
    private PointMarkBiz pointMarkBiz;





    @Test
    public void getCurrentDateTime() {
        String value = requestSetDAO.getCurrentDateTime();
        log.info("------------------------------------------------");
        log.info(value);
        log.info("------------------------------------------------");
    }

    @Test
    public void getPointMarkList(){
        String cv_req_id_lists = "A B C";
        String[] cv_req_id_list = cv_req_id_lists.split(" ");

        List<PointMark> pointMarkList = pointMarkBiz.getPointMarkList(cv_req_id_lists);
        log.info("pointMarkList >>>>   : "+pointMarkList);
    }

}
