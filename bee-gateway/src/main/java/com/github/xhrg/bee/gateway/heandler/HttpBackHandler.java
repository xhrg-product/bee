package com.github.xhrg.bee.gateway.heandler;

import com.github.xhrg.bee.gateway.api.Context;
import com.github.xhrg.bee.gateway.netty.NettyRequestHandler;
import com.github.xhrg.bee.gateway.router.HttpRouter;
import io.netty.channel.ChannelHandler;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@ChannelHandler.Sharable
@Component
public class HttpBackHandler extends NettyRequestHandler {

    @Autowired
    private HttpRouter httpRouter;

    public void doReaderHttpRequest(FullHttpRequest req, FullHttpResponse response, Context context) {
        String uri = req.uri();

        if (1 == 1) {
            httpRouter.doRouter(req, response, context);
        }
    }
}
