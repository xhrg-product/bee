package com.github.xhrg.bee.gateway.api;

import com.github.xhrg.bee.gateway.http.HttpRequestExt;
import com.github.xhrg.bee.gateway.http.HttpResponseExt;

public interface Filter {

    String name();

    FilterType type();

    Flow doFilter(HttpRequestExt request,
                     HttpResponseExt response, RequestContext requestContext);

    int sort();
}
