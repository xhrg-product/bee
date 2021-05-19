package com.github.xhrg.bee.netty;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class NettyBeans {

    @Autowired
    private HttpRequestHandler httpRequestHandler;

    @Bean
    public NettyHttpServer newBean() {
        return new NettyHttpServer(httpRequestHandler);
    }
}
