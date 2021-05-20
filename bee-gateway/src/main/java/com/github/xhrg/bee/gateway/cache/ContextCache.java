package com.github.xhrg.bee.gateway.cache;

import com.github.xhrg.bee.gateway.api.Context;
import com.github.xhrg.bee.gateway.util.ChannelUtils;
import io.netty.channel.Channel;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class ContextCache {

    private static Map<String, Context> map = new ConcurrentHashMap<>();

    public void putContext(Channel frontChannel, Context context) {
        map.put(ChannelUtils.id(frontChannel), context);
    }

    public Context getContext(Channel frontChannel) {
        return map.get(ChannelUtils.id(frontChannel));
    }
}
