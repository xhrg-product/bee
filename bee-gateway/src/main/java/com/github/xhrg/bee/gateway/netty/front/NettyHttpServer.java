package com.github.xhrg.bee.gateway.netty.front;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
@Slf4j
public class NettyHttpServer implements ApplicationRunner {

    @Resource
    private HttpFrontHandler httpFrontHandler;

    @Value("${gateway.port:10000}")
    private int port;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        this.start();
    }

    public void start() throws Exception {
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        //boss线程一般是1，如果你是多端口监听，才是大于1的值
        NioEventLoopGroup boss = new NioEventLoopGroup(1);
        NioEventLoopGroup worker = new NioEventLoopGroup(Runtime.getRuntime().availableProcessors());
        ChannelFuture channelFuture = serverBootstrap
                .group(boss, worker)
                .channel(NioServerSocketChannel.class)
                //.option(EpollChannelOption.SO_REUSEPORT, true)
                //.option(ChannelOption.SO_REUSEADDR, true)
                .option(ChannelOption.SO_BACKLOG, 1024)
                .option(ChannelOption.SO_REUSEADDR, true)
                .option(ChannelOption.SO_KEEPALIVE, false)
                .childOption(ChannelOption.TCP_NODELAY, true)
                .childOption(ChannelOption.SO_SNDBUF, 65535)
                .childOption(ChannelOption.SO_RCVBUF, 65535)

                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    protected void initChannel(NioSocketChannel ch) {
                        ChannelPipeline pipe = ch.pipeline();
                        pipe.addLast("encoder", new HttpResponseEncoder());
                        pipe.addLast("decoder", new HttpRequestDecoder());
                        pipe.addLast("aggregator", new HttpObjectAggregator(10 * 1024 * 1024));
                        pipe.addLast(httpFrontHandler);
                    }
                })
                .bind(this.port);
        channelFuture.addListener(future -> {
            if (future.cause() != null) {
                future.cause().printStackTrace();
            }
        });
        log.info("netty server started, please visit http://127.0.0.1:{}/ping", this.port);
    }
}
