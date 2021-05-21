package com.github.xhrg.bee.gateway.caller;

import com.github.xhrg.bee.basic.bo.ApiRuntimeContext;
import com.github.xhrg.bee.gateway.api.Flow;
import com.github.xhrg.bee.gateway.api.RequestContext;
import com.github.xhrg.bee.gateway.filter.FilterService;
import com.github.xhrg.bee.gateway.http.HttpRequestExt;
import com.github.xhrg.bee.gateway.http.HttpResponseExt;
import com.github.xhrg.bee.gateway.inner.InnerService;
import com.github.xhrg.bee.gateway.load.DataLoadService;
import com.github.xhrg.bee.gateway.netty.front.HttpFrontHandler;
import com.github.xhrg.bee.gateway.router.RouterHandler;
import io.netty.channel.Channel;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Objects;

@Component
public class Caller {

    @Resource
    private RouterHandler routerHandler;

    @Resource
    private FilterService filterService;

    @Resource
    private DataLoadService dataLoadService;

    @Resource
    private InnerService innerService;

    @Resource
    private HttpFrontHandler httpFrontHandler;

    //执行后置过滤器，然后回写数据给前端
    public void doPost(HttpRequestExt req, HttpResponseExt response, RequestContext requestContext) {

        filterService.post(req, response, requestContext);
        //得到后台返回的响应，直接写会给前端
        httpFrontHandler.writeToFront(requestContext.getChannelFront(), requestContext.getHttpResponseExt());
    }

    public Flow doPre(HttpRequestExt req, HttpResponseExt response, RequestContext requestContext) {
        String url = req.uri();

        Flow flow = innerService.doInner(url, response);
        if (Flow.isEnd(flow)) {
            return flow;
        }

        ApiRuntimeContext apiRunBo = dataLoadService.match(url);
        if (apiRunBo == null) {
            return Flow.END;
        }
        requestContext.setApiRuntimeContext(apiRunBo);

        flow = filterService.pre(req, response, requestContext);
        if (Flow.isEnd(flow)) {
            return flow;
        }

        if (Objects.equals(apiRunBo.getRouterBo().getType(), "http")) {
            routerHandler.route(req, response, requestContext);
            return Flow.GO;
        }
        return Flow.END;
    }
}
