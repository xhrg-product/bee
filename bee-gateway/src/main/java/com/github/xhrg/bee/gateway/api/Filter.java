package com.github.xhrg.bee.gateway.api;

import com.github.xhrg.bee.gateway.http.HttpResponseExt;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;

public interface Filter {

    String name();

    FilterType type();

    boolean doFilter(FullHttpRequest request,
                     HttpResponseExt response, Context context);

    int sort();

}
