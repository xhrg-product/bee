package com.github.xhrg.bee.admin.config;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Ping {

    @GetMapping("/ping")
    public String ping() {
        return "pong";
    }
}
