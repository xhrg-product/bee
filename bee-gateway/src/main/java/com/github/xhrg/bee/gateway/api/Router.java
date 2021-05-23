package com.github.xhrg.bee.gateway.api;

import com.github.xhrg.bee.basic.bo.FilterBo;
import com.github.xhrg.bee.basic.bo.RouterBo;
import com.github.xhrg.bee.gateway.http.HttpRequestExt;
import com.github.xhrg.bee.gateway.http.HttpResponseExt;

public interface Router {

    RouterBo init(RouterBo routerBo);

    void doRouter(HttpRequestExt request,
                  HttpResponseExt response, RequestContext requestContext);

    String name();

}
