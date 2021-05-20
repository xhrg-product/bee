package com.github.xhrg.bee.gateway.router;

import com.github.xhrg.bee.gateway.api.Context;
import com.github.xhrg.bee.gateway.http.HttpResponseExt;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RouterHandler {

    @Autowired
    private HttpRouter httpRouter;

    public void route(FullHttpRequest req, HttpResponseExt response, Context context) {
        httpRouter.doRouter(req, response, context);
    }
}
