package com.github.xhrg.bee.gateway.heandler;

import com.github.xhrg.bee.gateway.api.Context;
import com.github.xhrg.bee.gateway.netty.NettyRequestHandler;
import com.github.xhrg.bee.gateway.router.HttpRouter;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import org.springframework.beans.factory.annotation.Autowired;

public class HttpHandler extends NettyRequestHandler {

    @Autowired
    private HttpRouter httpRouter;

    public void doReaderHttpRequest(FullHttpRequest req, FullHttpResponse response, Context context) {


        String uri = req.uri();

        if (1 == 1) {
            httpRouter.doRouter(req, response, context);
        }


    }
}
