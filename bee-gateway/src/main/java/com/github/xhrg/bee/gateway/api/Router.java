package com.github.xhrg.bee.gateway.api;

import com.github.xhrg.bee.gateway.http.HttpRequestExt;
import com.github.xhrg.bee.gateway.http.HttpResponseExt;
import io.netty.handler.codec.http.HttpRequest;

public interface Router {

    void doRouter(HttpRequestExt request,
                  HttpResponseExt response, Context context);

}
