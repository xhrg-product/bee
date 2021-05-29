package com.github.xhrg.bee.gateway.api;

import com.github.xhrg.bee.gateway.http.HttpRequestExt;
import com.github.xhrg.bee.gateway.http.HttpResponseExt;
import com.github.xhrg.bee.gateway.load.data.RouterData;

public interface Router {

    void init(RouterData routerData);

    void doRouter(HttpRequestExt request,
                  HttpResponseExt response, RequestContext requestContext);

    String name();

}
