package com.github.xhrg.bee.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.github.xhrg.bee"})
public class BeeGatewayMain {

    public static void main(String[] args) {
        SpringApplication.run(BeeGatewayMain.class);
    }
}
