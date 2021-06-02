package com.github.xhrg.bee.gateway.api;

import com.github.xhrg.bee.gateway.http.HttpRequestExt;
import com.github.xhrg.bee.gateway.http.HttpResponseExt;

public interface PreFilter extends Filter {

    //执行过滤器
    Flow doPreFilter(HttpRequestExt request,
                     HttpResponseExt response, RequestContext requestContext);

}
