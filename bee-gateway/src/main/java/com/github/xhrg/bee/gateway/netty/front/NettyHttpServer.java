package com.github.xhrg.bee.gateway.netty.front;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.epoll.EpollChannelOption;
import io.netty.channel.nio.NioEventLoop;
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
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
@Slf4j
public class NettyHttpServer implements ApplicationRunner, ApplicationListener<ContextClosedEvent> {

    @Resource
    private HttpFrontHandler httpFrontHandler;

    private NioEventLoopGroup boss;

    private NioEventLoopGroup selector;

    @Value("${gateway.port:10000}")
    private int port;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        this.start();
    }

    public void start() throws Exception {
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        //boss线程一般是1，如果你是多端口监听，才是大于1的值
        boss = new NioEventLoopGroup(1);
        selector = new NioEventLoopGroup(Runtime.getRuntime().availableProcessors());
        serverBootstrap
                .group(boss, selector)
                .channel(NioServerSocketChannel.class)
                //linux下的单进程多端口
                .option(EpollChannelOption.SO_REUSEPORT, true)

                .option(ChannelOption.SO_REUSEADDR, true)
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
                        //HttpRequestDecoder有几个设置数据大小的参数，如果请求数据不大于这个参数，实际上这个参数也不用关注
                        //如果http请求数据的大小超过了这几个默认参数，那就不得不配置了。
                        pipe.addLast("decoder", new HttpRequestDecoder());
                        //基于netty做文件上传的时候，如果数据很大的时候，接受到的httpRequest并不完整。
                        //详情搜索：http chunked,当使用了HttpObjectAggregator后，则会把这些数据合并成一个请求对象
                        pipe.addLast("aggregator", new HttpObjectAggregator(10 * 1024 * 1024));
                        pipe.addLast(httpFrontHandler);
                    }
                });
        ChannelFuture channelFuture = serverBootstrap.bind(this.port).sync();
        if (channelFuture.isSuccess()) {
            log.info("netty server started, please visit http://127.0.0.1:{}/ping", this.port);
        } else {
            log.error("netty server started fail, port is " + this.port + ", please check", channelFuture.cause());
        }
    }

    //优雅下线netty。
    @Override
    public void onApplicationEvent(ContextClosedEvent event) {
        try {
            log.info("bee-gateway stop netty, will stop boss and selector !");
            boss.shutdownGracefully().sync();

            log.info("bee-gateway stop netty, shutdownAndWait !");
            httpFrontHandler.shutdownAndWait();

            log.info("bee-gateway stop netty, stop boss done, will stop selector !");
            selector.shutdownGracefully().sync();
            log.info("bee-gateway success stop boss and selector!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
