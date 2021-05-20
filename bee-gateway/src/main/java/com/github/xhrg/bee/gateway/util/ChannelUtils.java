package com.github.xhrg.bee.gateway.util;

import io.netty.channel.Channel;

public abstract class ChannelUtils {

    public static String id(Channel channel) {
        return channel.id().asLongText();
    }
}
