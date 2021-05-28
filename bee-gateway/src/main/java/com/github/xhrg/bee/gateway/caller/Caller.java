package com.github.xhrg.bee.gateway.caller;

import com.github.xhrg.bee.gateway.load.ApiExtService;
import com.github.xhrg.bee.gateway.load.ApiRuntimeContext;
import com.github.xhrg.bee.gateway.api.Flow;
import com.github.xhrg.bee.gateway.api.RequestContext;
import com.github.xhrg.bee.gateway.filter.FilterHandler;
import com.github.xhrg.bee.gateway.http.HttpRequestExt;
import com.github.xhrg.bee.gateway.http.HttpResponseExt;
import com.github.xhrg.bee.gateway.inner.InnerService;
import com.github.xhrg.bee.gateway.netty.front.HttpFrontHandler;
import com.github.xhrg.bee.gateway.router.RouterHandler;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class Caller {

    @Resource
    private RouterHandler routerHandler;

    @Resource
    private FilterHandler filterHandler;

    @Resource
    private ApiExtService apiExtService;

    @Resource
    private InnerService innerService;

    @Resource
    private HttpFrontHandler httpFrontHandler;

    //执行后置过滤器，然后回写数据给前端
    public void doPost(HttpRequestExt req, HttpResponseExt response, RequestContext requestContext) {
        filterHandler.post(req, response, requestContext);
        //得到后台返回的响应，直接写会给前端
        httpFrontHandler.writeToFront(requestContext.getChannelFront(), response);
    }

    public Flow doPre(HttpRequestExt req, HttpResponseExt response, RequestContext requestContext) {
        String url = req.uri();

        Flow flow = innerService.doInner(url, response);
        if (Flow.isEnd(flow)) {
            return flow;
        }

        ApiRuntimeContext apiRuntimeContext = apiExtService.match(url);
        if (apiRuntimeContext == null) {
            response.setHttpCode(404);
            response.setBody("not fund api by bee gateway");
            return Flow.END;
        }
        requestContext.setApiRuntimeContext(apiRuntimeContext);

        flow = filterHandler.pre(req, response, requestContext);
        if (Flow.isEnd(flow)) {
            return Flow.END;
        }
        routerHandler.route(req, response, requestContext);
        return Flow.GO;
    }
}
