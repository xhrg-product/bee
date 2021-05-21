package com.github.xhrg.bee.gateway.router;

import com.github.xhrg.bee.gateway.api.Context;
import com.github.xhrg.bee.gateway.http.HttpResponseExt;
import io.netty.handler.codec.http.FullHttpRequest;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class RouterHandler {

    @Resource
    private HttpRouter httpRouter;

    public void route(FullHttpRequest req, HttpResponseExt response, Context context) {
        httpRouter.doRouter(req, response, context);
    }
}
