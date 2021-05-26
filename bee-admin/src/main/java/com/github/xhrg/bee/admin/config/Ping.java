package com.github.xhrg.bee.admin.config;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Ping {

    @RequestMapping("/ping")
    public Object ping() {
        return "pong";
    }
}
