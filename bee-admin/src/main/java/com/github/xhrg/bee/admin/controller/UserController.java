package com.github.xhrg.bee.admin.controller;


import com.github.xhrg.bee.admin.util.ResponseUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    @PostMapping("/login")
    public Object login(Map<String, Object> map) {
        return ResponseUtils.data("mock_token");
    }

    @GetMapping("/info")
    public Object info(Map<String, Object> map) {
        Map<String, Object> returnMap = new HashMap<>();
        returnMap.put("name", "mockName");//
        returnMap.put("avatar", "https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
        returnMap.put("roles", Arrays.asList("editor"));
        returnMap.put("introduction", "张三");
        return ResponseUtils.data(returnMap);
    }
}
