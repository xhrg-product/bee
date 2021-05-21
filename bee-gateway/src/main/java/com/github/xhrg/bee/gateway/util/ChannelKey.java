package com.github.xhrg.bee.gateway.util;

import com.github.xhrg.bee.gateway.api.Context;
import io.netty.channel.Channel;
import io.netty.util.AttributeKey;

public abstract class ChannelKey {

    public static final AttributeKey<Channel> OTHER_CHANNEL = AttributeKey.valueOf("CHANNEL_BACK_KEY");

    public static final AttributeKey<Context> CHANNEL_CONTEXT_KEY = AttributeKey.valueOf("CHANNEL_FRONT_KEY");

}
