package com.github.xhrg.bee.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;

public class NettyHttpServer {

    private NettyRequestHandler httpRequestHandler;

    public NettyHttpServer(NettyRequestHandler httpRequestHandler) {
        this.httpRequestHandler = httpRequestHandler;
    }

    public void start() throws Exception {
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        //boss线程一般是1，如果你是多端口监听，才是大于1的值
        NioEventLoopGroup boss = new NioEventLoopGroup(1);
        NioEventLoopGroup worker = new NioEventLoopGroup(Runtime.getRuntime().availableProcessors());
        serverBootstrap
                .group(boss, worker)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    protected void initChannel(NioSocketChannel ch) {
                        ChannelPipeline pipe = ch.pipeline();
                        pipe.addLast("encoder", new HttpResponseEncoder());
                        pipe.addLast("decoder", new HttpRequestDecoder());
                        pipe.addLast("aggregator", new HttpObjectAggregator(10 * 1024 * 1024));
                        pipe.addLast(httpRequestHandler);
                    }
                })
                .bind(8000);
        System.out.println("netty http start");
    }
}
