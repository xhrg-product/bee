package com.github.xhrg.bee.gateway.router;

import com.alibaba.fastjson.JSON;
import com.github.xhrg.bee.basic.bo.DubboRouterBo;
import com.github.xhrg.bee.gateway.api.RequestContext;
import com.github.xhrg.bee.gateway.api.Router;
import com.github.xhrg.bee.gateway.caller.Caller;
import com.github.xhrg.bee.gateway.http.HttpRequestExt;
import com.github.xhrg.bee.gateway.http.HttpResponseExt;

import javax.annotation.Resource;
import java.util.concurrent.CompletableFuture;

public class DubboRouter implements Router {

    @Resource
    private Caller caller;

    @Override
    public void doRouter(HttpRequestExt request, HttpResponseExt response, RequestContext requestContext) {

        String data = request.getBody();
        DubboRouterBo dubboRouterBo = (DubboRouterBo) requestContext.getApiRuntimeContext().getRouterBo();
        CompletableFuture<Object> afuture = dubboRouterBo.getGenericService().$invokeAsync(dubboRouterBo.getMethod(),
                dubboRouterBo.getParamType(), new Object[]{data});
        afuture.whenComplete((value, throwable) -> {
            if (throwable != null) {
                response.setHttpCode(502);
                response.setBody("dubbo error, " + throwable.getMessage());
                caller.doPost(request, response, requestContext);
                return;
            }
            response.setBody(JSON.toJSONString(value));
            caller.doPost(request, response, requestContext);
        });
    }
}
