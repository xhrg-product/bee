package com.github.xhrg.bee.demo.http;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class Query {

    //http://127.0.0.1:9001/bee-demo/query
    @RequestMapping("/query")
    public Object query() {
        Map<String, Object> map = new HashMap<>();
        map.put("name", "order");
        map.put("num", 100);
        return map;
    }
}
