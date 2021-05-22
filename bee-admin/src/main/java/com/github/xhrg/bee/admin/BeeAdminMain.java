package com.github.xhrg.bee.admin;

import com.github.xhrg.bee.basic.mapper.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackageClasses = Mapper.class)
public class BeeAdminMain {

    public static void main(String[] args) {
        SpringApplication.run(BeeAdminMain.class);
    }
}
