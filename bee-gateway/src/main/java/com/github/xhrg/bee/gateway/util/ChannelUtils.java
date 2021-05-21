package com.github.xhrg.bee.gateway.util;

import com.github.xhrg.bee.gateway.api.Context;
import io.netty.channel.Channel;

public abstract class ChannelUtils {

    public static Context getContextByBackChannel(Channel channelBack) {
        return channelBack.attr(ChannelKey.OTHER_CHANNEL).get().attr(ChannelKey.CHANNEL_CONTEXT_KEY).get();
    }

    public static void closeChannel(Channel channel) {
        Channel channelOther = channel.attr(ChannelKey.OTHER_CHANNEL).getAndSet(null);
        if (channelOther != null) {
            channelOther.close();
        }
    }
}
