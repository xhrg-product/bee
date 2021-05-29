package com.github.xhrg.bee.gateway.router;

import com.alibaba.fastjson.JSON;
import com.github.xhrg.bee.gateway.api.RequestContext;
import com.github.xhrg.bee.gateway.api.Router;
import com.github.xhrg.bee.gateway.exp.BadException;
import com.github.xhrg.bee.gateway.exp.DataException;
import com.github.xhrg.bee.gateway.load.data.RouterData;
import com.github.xhrg.bee.gateway.router.data.HttpRouterData;
import com.github.xhrg.bee.gateway.http.HttpRequestExt;
import com.github.xhrg.bee.gateway.http.HttpResponseExt;
import com.github.xhrg.bee.gateway.netty.back.NettyHttpClient;
import com.github.xhrg.bee.gateway.util.PathMatcher;
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
    public void init(RouterData routerData) {
        HttpRouterData httpRouterBo = JSON.parseObject(routerData.getData(), HttpRouterData.class);
        BeanUtils.copyProperties(routerData, httpRouterBo);
        try {
            URL url = new URL(httpRouterBo.getTargetUrl());
            httpRouterBo.setHost(url.getHost());
            httpRouterBo.setPort(url.getPort());
            httpRouterBo.setTargetPath(url.getPath());
        } catch (Exception e) {
            throw new DataException(e.getMessage());
        }
        routerData.setDynaObject(httpRouterBo);
    }

    @Override
    public void doRouter(HttpRequestExt request, HttpResponseExt response, RequestContext requestContext) {
        Channel channelFront = requestContext.getChannelFront();
        try {
            HttpRouterData httpRouterData = requestContext.getApiRuntimeContext().getRouterData().getDynaObject();
            String pattern = requestContext.getApiRuntimeContext().getApiData().getPath();
            String path = request.getUri();
            String target = httpRouterData.getTargetPath();
            String newPath = PathMatcher.toNewPath(pattern, path, target);
            request.setUri(newPath);
            nettyHttpClient.write(request, channelFront, httpRouterData.getHost(), httpRouterData.getPort(), requestContext);
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
