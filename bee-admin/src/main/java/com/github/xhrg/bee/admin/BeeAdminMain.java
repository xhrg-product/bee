package com.github.xhrg.bee.admin;

import com.github.xhrg.bee.basic.mapper.Mapper;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.github.xhrg.bee"})
@MapperScan(basePackageClasses = Mapper.class)
public class BeeAdminMain {

    public static void main(String[] args) {
        SpringApplication.run(BeeAdminMain.class);
    }
}
