package com.github.xhrg.bee.gateway.router;

import com.github.xhrg.bee.gateway.api.Context;
import com.github.xhrg.bee.gateway.api.Router;
import com.github.xhrg.bee.gateway.netty.back.NettyHttpClient;
import io.netty.channel.Channel;
import io.netty.handler.codec.http.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.net.URLDecoder;

@Component
public class HttpRouter implements Router {

    private static String HttpRouterIPkey = "HttpRouterIPkey";

    private static String CHANNEL_KEY = "CHANNEL_KEY";

    @Autowired
    private NettyHttpClient nettyHttpClient;

    @Override
    public void doRouter(FullHttpRequest request, FullHttpResponse response, Context context) {
        Channel channelFront = context.getChannelFront();
        String urlString = context.getApiRunBo().getRouterBo().getTargetUrl();
        try {
            URL url = new URL(urlString);
            request.setUri(url.toURI().getPath());
            nettyHttpClient.write(request, channelFront, url.getHost(), url.getPort());
        } catch (Exception e) {
        }
    }
}
