package com.github.xhrg.bee.gateway.netty.back;

import com.github.xhrg.bee.gateway.util.ChannelKey;
import com.github.xhrg.bee.gateway.util.HttpUtilsExt;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.*;
import io.netty.util.Attribute;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class NettyHttpClient {

    @Resource
    private HttpBackHandler httpBackHandler;

    private AtomicInteger a = new AtomicInteger(0);

    private AtomicInteger b = new AtomicInteger(0);

    public void write(FullHttpRequest fullHttpRequest, Channel channelFront, String host, int port) {

        Attribute<Channel> attr = channelFront.attr(ChannelKey.OTHER_CHANNEL);
        Channel channelBack = attr.get();
        if (channelBack != null) {
            System.out.println("链接复用"+a.getAndIncrement());
            channelBack.writeAndFlush(fullHttpRequest);
            return;
        }
        Bootstrap cb = new Bootstrap().group(channelFront.eventLoop())
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<Channel>() {
                    public void initChannel(Channel ch) throws Exception {
                        ChannelPipeline pipe = ch.pipeline();
                        pipe.addLast("decoder", new HttpResponseDecoder());
                        pipe.addLast("encoder", new HttpRequestEncoder());
                        pipe.addLast("aggregator", new HttpObjectAggregator(10 * 1024 * 1024));
                        pipe.addLast(httpBackHandler);
                    }
                });
        try {
            System.out.println("创建后端链接"+b.getAndIncrement());
            ChannelFuture channelFuture = cb.connect(host, port);
            channelFuture.addListener(future -> {
                //如果创建链接失败
                if (!future.isSuccess()) {
                    channelFront.writeAndFlush(HttpUtilsExt.connectionErrorResponse());
                    return;
                }
                Channel channelNewBack = channelFuture.channel();

                //对向前和向后的channel进行一个双向绑定。
                channelFront.attr(ChannelKey.OTHER_CHANNEL).set(channelNewBack);
                channelNewBack.attr(ChannelKey.OTHER_CHANNEL).set(channelFront);

                channelNewBack.writeAndFlush(fullHttpRequest);
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
