package com.github.xhrg.bee.gateway.util;

import com.github.xhrg.bee.gateway.api.RequestContext;
import io.netty.channel.Channel;
import io.netty.util.AttributeKey;

public abstract class ChannelKey {

    public static final AttributeKey<Channel> OTHER_CHANNEL = AttributeKey.valueOf("CHANNEL_BACK_KEY");

    public static final AttributeKey<RequestContext> CHANNEL_REQUEST_CONTEXT = AttributeKey.valueOf("CHANNEL_REQUEST_CONTEXT");

}
