package com.github.xhrg.bee.demo.springcloud;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SpringHelloServiceImpl {

    @RequestMapping("/spring/sayHello")
    public String sayHello() {
        return "spring__";
    }
}
