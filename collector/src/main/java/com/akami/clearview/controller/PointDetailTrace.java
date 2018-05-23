package com.akami.clearview.controller;

import com.akami.clearview.domain.PointMark;
import com.akami.clearview.service.PointMarkBiz;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.inject.Inject;
import java.util.List;

/**
 * Created by akamikang on 2017. 7. 14..
 */
@Slf4j
@Controller
public class PointDetailTrace {

    @Inject
    private PointMarkBiz pointMarkBiz;




    @RequestMapping(value = "/pointDetailTrace", method = RequestMethod.POST)
    public String  helloMysql(String cv_req_id_list, Model model) throws Exception{
        System.out.println("cv_req_id_list : "+ cv_req_id_list);
        log.info("helloMysql..................."+cv_req_id_list);

        List<PointMark>  pointMarkList = pointMarkBiz.getPointMarkList(cv_req_id_list);

        model.addAttribute("pointMarkList", pointMarkList);
        System.out.println(pointMarkList);

        return "pointDetailTrace";

    }
}
