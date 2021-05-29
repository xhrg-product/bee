package com.github.xhrg.bee.gateway.router;

import com.github.xhrg.bee.gateway.api.RequestContext;
import com.github.xhrg.bee.gateway.api.Router;
import com.github.xhrg.bee.gateway.http.HttpRequestExt;
import com.github.xhrg.bee.gateway.http.HttpResponseExt;
import com.github.xhrg.bee.gateway.load.data.RouterData;

public class SpringCloudRouter implements Router {

    @Override
    public void init(RouterData routerData) {
        String data = routerData.getData();
    }

    @Override
    public void doRouter(HttpRequestExt request, HttpResponseExt response, RequestContext requestContext) {

    }

    @Override
    public String name() {
        return null;
    }
}
