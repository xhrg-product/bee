package com.github.xhrg.bee.gateway.netty;

import com.github.xhrg.bee.gateway.cache.ChannelCache;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class NettyHttpClient {

    @Autowired
    private ChannelCache channelCache;

    public Channel create(String remoteAddress, Channel channelFront) {

        Channel channelBack = channelCache.getByFront(channelFront);
        if (channelBack != null) {
            return channelBack;
        }
        Bootstrap cb = new Bootstrap().group(channelFront.eventLoop())
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<Channel>() {
                    public void initChannel(Channel ch) throws Exception {
                    }
                });
        try {
            ChannelFuture channelFuture = cb.connect(remoteAddress, 80).sync();
            Channel channelback = channelFuture.channel();
            channelCache.put2Channel(channelFront, channelBack);
            return channelBack;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
