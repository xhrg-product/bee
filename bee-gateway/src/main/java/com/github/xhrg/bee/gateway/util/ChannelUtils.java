package com.github.xhrg.bee.gateway.util;

import com.github.xhrg.bee.gateway.api.Context;
import io.netty.channel.Channel;

public abstract class ChannelUtils {

    public static Context getContextByBackChannel(Channel channelBack) {
        return channelBack.attr(ChannelKey.CHANNEL_FRONT_KEY).get().attr(ChannelKey.CHANNEL_CONTEXT_KEY).get();
    }
}
