package com.github.xhrg.bee.gateway.api;

import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;

public interface Filter {

    String name();

    //pre or post
    //pre表示前置处理，post表示后置处理
    String type();

    boolean doFilter(FullHttpRequest request,
                     FullHttpResponse response, Context context);

}
