package com.github.xhrg.bee.gateway.netty.back;

import com.github.xhrg.bee.gateway.cache.ChannelCache;
import com.github.xhrg.bee.gateway.netty.back.HttpBackHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class NettyHttpClient {

    @Autowired
    private ChannelCache channelCache;

    @Autowired
    private HttpBackHandler httpBackHandler;

    public void write(FullHttpRequest fullHttpRequest, Channel channelFront, String remoteAddress) {
        Channel channelback = channelCache.getByFront(channelFront);
        if (channelback != null) {
            channelback.writeAndFlush(fullHttpRequest);
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
            ChannelFuture channelFuture = cb.connect(remoteAddress, 80);
            channelFuture.addListener(future -> {
                Channel channel = channelFuture.channel();
                channelCache.put2Channel(channelFront, channel);
                channel.writeAndFlush(fullHttpRequest);
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
