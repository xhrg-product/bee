package com.github.xhrg.bee.gateway.router;

import com.github.xhrg.bee.gateway.api.RequestContext;
import com.github.xhrg.bee.gateway.http.HttpRequestExt;
import com.github.xhrg.bee.gateway.http.HttpResponseExt;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class RouterHandler {

    @Resource
    private HttpRouter httpRouter;

    public void route(HttpRequestExt req, HttpResponseExt response, RequestContext requestContext) {
        httpRouter.doRouter(req, response, requestContext);
    }
}
