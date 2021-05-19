package com.github.xhrg.bee.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.SocketAddress;

public class NettyHttpClient {
    public void init(SocketAddress remoteAddress, EventLoopGroup group) {
        Bootstrap cb = new Bootstrap().group(group)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<Channel>() {
                    public void initChannel(Channel ch) throws Exception {

                    }
                });
        cb.connect(remoteAddress);
    }
}
