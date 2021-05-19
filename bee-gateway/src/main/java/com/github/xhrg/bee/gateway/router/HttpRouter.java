package com.github.xhrg.bee.gateway.router;

import com.github.xhrg.bee.gateway.api.Context;
import com.github.xhrg.bee.gateway.api.Router;
import com.github.xhrg.bee.gateway.cache.ChannelCache;
import com.github.xhrg.bee.gateway.netty.NettyHttpClient;
import com.sun.tracing.dtrace.Attributes;
import io.netty.channel.Channel;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class HttpRouter implements Router {

    private static String HttpRouterIPkey = "HttpRouterIPkey";

    private static String CHANNEL_KEY = "CHANNEL_KEY";

    @Autowired
    private NettyHttpClient nettyHttpClient;

    @Override
    public void doRouter(FullHttpRequest request, FullHttpResponse response, Context context) {
        Channel channel = context.get(CHANNEL_KEY);
        Channel channel1 = nettyHttpClient.create("", channel);
        channel1.writeAndFlush(request);
    }
}
