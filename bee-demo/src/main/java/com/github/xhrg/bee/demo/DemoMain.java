package com.github.xhrg.bee.demo;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude={DataSourceAutoConfiguration.class})
//@EnableDubbo
public class DemoMain {

    public static void main(String[] args) {
        SpringApplication.run(DemoMain.class);
    }
}
