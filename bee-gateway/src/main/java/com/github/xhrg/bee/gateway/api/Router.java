package com.github.xhrg.bee.gateway.api;

import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;

public interface Router {

    void doRouter(FullHttpRequest request,
                     FullHttpResponse response, Context context);

}
