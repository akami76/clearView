package com.akami.clearview.service;

import com.akami.clearview.domain.PointMark;
import com.akami.clearview.db.PointMarkDAO;
import org.springframework.beans.factory.annotation.Autowired;

import javax.inject.Named;
import java.util.List;

/**
 * Created by akamikang on 2017. 7. 17..
 */


@Named
public class PointMarkBiz {

    @Autowired
    PointMarkDAO pointMarkDAO;

    public List<PointMark> getPointMarkList(String cv_req_id_lists){
        List<PointMark> pointMarkList = null;
        String[] cv_req_id_list = cv_req_id_lists.split(" ");

        pointMarkList = pointMarkDAO.getPointMarkList(cv_req_id_list);

        return pointMarkList;
    }
}
