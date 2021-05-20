package com.github.xhrg.bee.gateway;

import com.github.xhrg.bee.basic.mapper.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.github.xhrg.bee"})
@MapperScan(basePackageClasses = Mapper.class)
public class BeeGatewayMain {

    public static void main(String[] args) {
        SpringApplication.run(BeeGatewayMain.class);
    }
}
