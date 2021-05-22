package com.github.xhrg.bee.gateway.router;

import com.github.xhrg.bee.gateway.api.RequestContext;
import com.github.xhrg.bee.gateway.api.Router;
import com.github.xhrg.bee.gateway.exp.BadException;
import com.github.xhrg.bee.gateway.load.extbo.HttpRouterBo;
import com.github.xhrg.bee.gateway.http.HttpRequestExt;
import com.github.xhrg.bee.gateway.http.HttpResponseExt;
import com.github.xhrg.bee.gateway.netty.back.NettyHttpClient;
import io.netty.channel.Channel;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class HttpRouter implements Router {

    @Resource
    private NettyHttpClient nettyHttpClient;

    @Override
    public void doRouter(HttpRequestExt request, HttpResponseExt response, RequestContext requestContext) {
        Channel channelFront = requestContext.getChannelFront();
        try {
            HttpRouterBo httpRouterBo = (HttpRouterBo) requestContext.getApiRuntimeContext().getRouterBo();
            nettyHttpClient.write(request, channelFront, httpRouterBo.getHost(), httpRouterBo.getPort(), requestContext);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BadException(e.getMessage());
        }
    }
}
