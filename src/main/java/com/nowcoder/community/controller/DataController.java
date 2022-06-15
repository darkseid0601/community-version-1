package com.nowcoder.community.controller;

import com.nowcoder.community.service.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.PipedOutputStream;
import java.util.Date;

/**
 * @BelongsProject: community-version-1
 * @BelongsPackage: com.nowcoder.community.controller
 * @CreateTime: 2022-06-15  19:43
 * @Description: 统计网站数据
 */
@Controller
public class DataController {

    @Autowired
    private DataService dataService;

    /**
     * @description: 统计页面
     * @date: 2022/6/15 21:50
     * @param: []
     * @return: java.lang.String
     **/
    @RequestMapping(path = "/data", method = {RequestMethod.GET, RequestMethod.POST})
    public String getDataPage() {
        return "/site/admin/data";
    }

    /**
     * @description: 统计网站UV
     * @date: 2022/6/15 21:50
     * @param: [start, end, model]
     * @return: java.lang.String
     **/
    @RequestMapping(path = "/data/uv", method = RequestMethod.POST)
    public String getUV(@DateTimeFormat(pattern = "yyyy-MM-dd")Date start,
                        @DateTimeFormat(pattern = "yyyy-MM-dd")Date end, Model model) {
        System.out.println(start);
        System.out.println(end);
        long uv = dataService.calculateUV(start, end);
        model.addAttribute("uvResult", uv);
        model.addAttribute("uvStartDate", start);
        model.addAttribute("uvEndDate", end);
        return "forward:/data";
    }

    @RequestMapping(path = "/data/dau", method = RequestMethod.POST)
    public String getDAU(@DateTimeFormat(pattern = "yyyy-MM-dd")Date start,
                         @DateTimeFormat(pattern = "yyyy-MM-dd")Date end, Model model) {
        long dau = dataService.calculateDAU(start, end);
        model.addAttribute("dauResult", dau);
        model.addAttribute("dauStartDate", start);
        model.addAttribute("dauEndDate", end);
        return "forward:/data";
    }



}
