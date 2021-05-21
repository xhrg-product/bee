package com.github.xhrg.bee.demo.dubbo;

import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.stereotype.Component;

import java.util.Date;

@DubboService
@Component
public class HelloServiceImpl implements HelloService {

    @Override
    public String sayHello(String name) {
        System.out.println("hello" + name + new Date());
        if (1 == 1)
            throw new RuntimeException("aa");
        return "Hello" + name;
    }
}