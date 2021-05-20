package com.github.xhrg.bee.gateway.api;

import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;

public interface Filter {

    String name();

    FilterType type();

    boolean doFilter(FullHttpRequest request,
                     FullHttpResponse response, Context context);

    int sort();

}
