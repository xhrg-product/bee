package com.github.xhrg.bee.gateway.router;

import com.github.xhrg.bee.gateway.api.Context;
import com.github.xhrg.bee.gateway.api.Router;
import com.github.xhrg.bee.gateway.exp.BadException;
import com.github.xhrg.bee.gateway.http.HttpRequestExt;
import com.github.xhrg.bee.gateway.http.HttpResponseExt;
import com.github.xhrg.bee.gateway.netty.back.NettyHttpClient;
import io.netty.channel.Channel;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.net.URL;

@Component
public class HttpRouter implements Router {

    @Resource
    private NettyHttpClient nettyHttpClient;

    @Override
    public void doRouter(HttpRequestExt request, HttpResponseExt response, Context context) {
        Channel channelFront = context.getChannelFront();
        try {
            String urlString = context.getApiRuntimeContext().getRouterBo().getTargetUrl();
            URL url = new URL(urlString);
            request.setUri(url.toURI().getPath());
            nettyHttpClient.write(request, channelFront, url.getHost(), url.getPort());
        } catch (Exception e) {
            e.printStackTrace();
            throw new BadException(e.getMessage());
        }
    }

}
