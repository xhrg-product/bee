package com.github.xhrg.bee.gateway.router;

import com.github.xhrg.bee.gateway.api.RequestContext;
import com.github.xhrg.bee.gateway.api.Router;
import com.github.xhrg.bee.gateway.exp.BadException;
import com.github.xhrg.bee.gateway.extbo.HttpRouterBo;
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
    public void doRouter(HttpRequestExt request, HttpResponseExt response, RequestContext requestContext) {
        Channel channelFront = requestContext.getChannelFront();
        try {
            HttpRouterBo httpRouterBo = (HttpRouterBo) requestContext.getApiRuntimeContext().getRouterBo();
            URL url = new URL(httpRouterBo.getTargetUrl());
            request.setUri(url.toURI().getPath());
            nettyHttpClient.write(request, channelFront, url.getHost(), url.getPort(), requestContext);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BadException(e.getMessage());
        }
    }

}
