package com.github.xhrg.bee.gateway.http;

import com.github.xhrg.bee.gateway.api.Context;
import com.github.xhrg.bee.gateway.api.Router;
import com.github.xhrg.bee.netty.NettyHttpClient;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;

public class HttpRouter implements Router {

    private static String HttpRouterIPkey = "HttpRouterIPkey";

    @Override
    public void doRouter(FullHttpRequest request, FullHttpResponse response, Context context) {


//        NettyHttpClient

    }
}
