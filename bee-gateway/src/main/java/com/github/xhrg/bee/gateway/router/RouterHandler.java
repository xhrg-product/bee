package com.github.xhrg.bee.gateway.router;

import com.github.xhrg.bee.gateway.api.Context;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RouterHandler {

    @Autowired
    private HttpRouter httpRouter;

    public void route(FullHttpRequest req, FullHttpResponse response, Context context) {
        httpRouter.doRouter(req, response, context);
    }
}
