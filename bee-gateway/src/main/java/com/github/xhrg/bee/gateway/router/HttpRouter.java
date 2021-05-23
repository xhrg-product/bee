package com.github.xhrg.bee.gateway.router;

import com.alibaba.fastjson.JSON;
import com.github.xhrg.bee.basic.bo.RouterBo;
import com.github.xhrg.bee.gateway.api.RequestContext;
import com.github.xhrg.bee.gateway.api.Router;
import com.github.xhrg.bee.gateway.exp.BadException;
import com.github.xhrg.bee.gateway.exp.DataException;
import com.github.xhrg.bee.gateway.router.bo.HttpRouterBo;
import com.github.xhrg.bee.gateway.http.HttpRequestExt;
import com.github.xhrg.bee.gateway.http.HttpResponseExt;
import com.github.xhrg.bee.gateway.netty.back.NettyHttpClient;
import io.netty.channel.Channel;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.net.URL;

@Component
public class HttpRouter implements Router {

    @Resource
    private NettyHttpClient nettyHttpClient;

    @Override
    public RouterBo init(RouterBo routerBo) {
        HttpRouterBo httpRouterBo = JSON.parseObject(routerBo.getData(), HttpRouterBo.class);
        BeanUtils.copyProperties(routerBo, httpRouterBo);
        try {
            URL url = new URL(httpRouterBo.getTargetUrl());
            httpRouterBo.setHost(url.getHost());
            httpRouterBo.setPort(url.getPort());
            httpRouterBo.setTargetPath(url.getPath());
        } catch (Exception e) {
            throw new DataException(e.getMessage());
        }
        return httpRouterBo;
    }

    @Override
    public void doRouter(HttpRequestExt request, HttpResponseExt response, RequestContext requestContext) {
        Channel channelFront = requestContext.getChannelFront();
        try {
            HttpRouterBo httpRouterBo = (HttpRouterBo) requestContext.getApiRuntimeContext().getRouterBo();
            //重写URL
            request.setUri(((HttpRouterBo) requestContext.getApiRuntimeContext().getRouterBo()).getTargetPath());
            nettyHttpClient.write(request, channelFront, httpRouterBo.getHost(), httpRouterBo.getPort(), requestContext);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BadException(e.getMessage());
        }
    }

    @Override
    public String name() {
        return "http";
    }
}
