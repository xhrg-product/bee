package com.github.xhrg.bee.admin.controller;

import com.github.xhrg.bee.admin.config.Response;
import com.github.xhrg.bee.admin.config.ResponseList;
import com.github.xhrg.bee.admin.service.ApisService;
import com.github.xhrg.bee.basic.bo.ApiBo;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/apis")
public class ApisController {

    @Resource
    private ApisService apisService;

    @RequestMapping("/query")
    public Object query() {

        List<ApiBo> list = apisService.query();

        ResponseList responseList = new ResponseList();
        responseList.setItems(list);
        responseList.setTotal(list.size());

        Response response = new Response();
        response.setData(responseList);

        return response;
    }


    @RequestMapping("/query1")
    public Object query1() {

        HashMap<String, Object> map = new HashMap<>();
        map.put("title", "1");
        map.put("author", "2");
        map.put("pageviews", "3");
        map.put("status", "4");
        map.put("display_time", "5");

        Map<String, Object> m = new HashMap<>();
        m.put("items", Arrays.asList(map));
        m.put("total", 1);


        Map<String, Object> m2 = new HashMap<>();
        m2.put("data", m);
        m2.put("code", 20000);

        return m2;
    }
}
