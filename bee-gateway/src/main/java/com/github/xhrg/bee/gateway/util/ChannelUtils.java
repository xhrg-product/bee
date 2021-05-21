package com.github.xhrg.bee.gateway.util;

import com.github.xhrg.bee.gateway.api.Context;
import io.netty.channel.Channel;

public abstract class ChannelUtils {

    public static Context getContextByBackChannel(Channel channelBack) {
        return channelBack.attr(ChannelKey.OTHER_CHANNEL).get().attr(ChannelKey.CHANNEL_CONTEXT_KEY).get();
    }

    public static void closeChannel(Channel channel) {
        //断开链接的时候，写一个response过去，防止另一端因为收不到响应一致pending状态
        Channel channelOther = channel.attr(ChannelKey.OTHER_CHANNEL).getAndSet(null);
        if (channelOther != null) {
            channelOther.writeAndFlush(HttpUtilsExt.emptyResponse());
            channelOther.close();
        }
        channel.writeAndFlush(HttpUtilsExt.emptyResponse());
        channel.close();
    }
}
