package com.github.xhrg.bee.gateway.router;

import com.github.xhrg.bee.gateway.api.Context;
import com.github.xhrg.bee.gateway.api.Router;
import com.github.xhrg.bee.gateway.netty.back.NettyHttpClient;
import io.netty.channel.Channel;
import io.netty.handler.codec.http.*;
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
        Channel channelFront = context.getChannelFront();
        nettyHttpClient.write(request, channelFront, "www.baidu.com");
    }
}
