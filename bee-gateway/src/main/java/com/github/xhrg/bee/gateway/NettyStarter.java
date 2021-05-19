package com.github.xhrg.bee.gateway;

import com.github.xhrg.bee.gateway.heandler.HttpHandler;
import com.github.xhrg.bee.netty.NettyHttpServer;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;

public class NettyStarter implements ApplicationRunner {

    @Override
    public void run(ApplicationArguments args) throws Exception {

        HttpHandler httpRequestHandler = new HttpHandler();
        NettyHttpServer nettyHttpServer = new NettyHttpServer(httpRequestHandler);
        nettyHttpServer.start();
    }

}
