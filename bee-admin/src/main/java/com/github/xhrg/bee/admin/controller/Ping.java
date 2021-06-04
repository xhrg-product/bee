package com.github.xhrg.bee.admin.controller;

import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class Ping {

    @GetMapping("/ping")
    public Object ping(HttpServletRequest httpRequest) {
        return "pong";
    }
}
