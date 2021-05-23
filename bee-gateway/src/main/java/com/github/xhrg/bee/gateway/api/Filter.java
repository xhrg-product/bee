package com.github.xhrg.bee.gateway.api;

import com.github.xhrg.bee.basic.bo.FilterBo;
import com.github.xhrg.bee.basic.bo.RouterBo;
import com.github.xhrg.bee.gateway.http.HttpRequestExt;
import com.github.xhrg.bee.gateway.http.HttpResponseExt;

public interface Filter {

    String name();

    FilterType type();

    FilterBo init(FilterBo filterBo);

    Flow doFilter(HttpRequestExt request,
                  HttpResponseExt response, RequestContext requestContext);

    int sort();
}
