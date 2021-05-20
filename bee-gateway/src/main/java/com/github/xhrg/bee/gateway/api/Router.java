package com.github.xhrg.bee.gateway.api;

import com.github.xhrg.bee.gateway.http.HttpResponseExt;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;

public interface Router {

    void doRouter(FullHttpRequest request,
                  HttpResponseExt response, Context context);

}
