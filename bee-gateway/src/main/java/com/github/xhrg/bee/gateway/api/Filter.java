package com.github.xhrg.bee.gateway.api;

import com.github.xhrg.bee.gateway.http.HttpRequestExt;
import com.github.xhrg.bee.gateway.http.HttpResponseExt;

public interface Filter {

    String name();

    FilterType type();

    boolean doFilter(HttpRequestExt request,
                     HttpResponseExt response, RequestContext requestContext);

    int sort();

}
