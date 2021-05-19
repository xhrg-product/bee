package com.github.xhrg.bee.gateway.netty;

import com.github.xhrg.bee.gateway.cache.ChannelCache;
import com.github.xhrg.bee.gateway.heandler.HttpBackHandler;
import com.github.xhrg.bee.gateway.heandler.HttpFrontHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class NettyHttpClient {

    @Autowired
    private ChannelCache channelCache;

    @Autowired
    private HttpBackHandler httpBackHandler;

    public Channel create(String remoteAddress, Channel channelFront) {

        Channel channelBack = channelCache.getByFront(channelFront);
        if (channelBack != null) {
            return channelBack;
        }
        Bootstrap cb = new Bootstrap().group(channelFront.eventLoop())
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<Channel>() {
                    public void initChannel(Channel ch) throws Exception {
                        ChannelPipeline pipe = ch.pipeline();
                        pipe.addLast("encoder", new HttpResponseEncoder());
                        pipe.addLast("decoder", new HttpRequestDecoder());
                        pipe.addLast("aggregator", new HttpObjectAggregator(10 * 1024 * 1024));
                        pipe.addLast(httpBackHandler);
                    }
                });
        try {
            ChannelFuture channelFuture = cb.connect(remoteAddress, 80).sync();
            Channel channelback = channelFuture.channel();
            channelCache.put2Channel(channelFront, channelback);
            return channelBack;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
