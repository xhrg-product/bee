package com.github.xhrg.bee.gateway.util;

import com.github.xhrg.bee.gateway.api.RequestContext;
import io.netty.channel.Channel;

public abstract class ChannelUtils {

    public static RequestContext getContextByBackChannel(Channel channelBack) {
        return channelBack.attr(ChannelKey.OTHER_CHANNEL).get().attr(ChannelKey.CHANNEL_REQUEST_CONTEXT).get();
    }

}
