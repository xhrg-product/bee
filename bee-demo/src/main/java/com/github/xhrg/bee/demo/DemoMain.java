package com.github.xhrg.bee.demo;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.apache.zookeeper.server.ZooKeeperServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@EnableDubbo
@EnableDiscoveryClient
public class DemoMain {

    public static void main(String[] args) {
        SpringApplication.run(DemoMain.class);
    }
}
