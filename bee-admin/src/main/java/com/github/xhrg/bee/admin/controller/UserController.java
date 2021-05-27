package com.github.xhrg.bee.admin.controller;


import com.github.xhrg.bee.admin.util.ResponseUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    @RequestMapping("/login")
    public Object login(Map<String, Object> map) {
        return ResponseUtils.data("mock_token");
    }

    @RequestMapping("/info")
    public Object info(Map<String, Object> map) {
        Map<String, Object> returnMap = new HashMap<>();
        returnMap.put("name", "mockName");
        returnMap.put("avatar", "avatar");
        return ResponseUtils.data(returnMap);
    }
}
