package com.github.xhrg.bee.gateway.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.nio.NioSocketChannel;

public class NettyHttpClient {

    public static Channel create(String remoteAddress, Channel channel) {
        Bootstrap cb = new Bootstrap().group(channel.eventLoop())
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<Channel>() {
                    public void initChannel(Channel ch) throws Exception {


                    }
                });
        try {
            ChannelFuture channelFuture = cb.connect(remoteAddress, 80).sync();
            return channelFuture.channel();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
