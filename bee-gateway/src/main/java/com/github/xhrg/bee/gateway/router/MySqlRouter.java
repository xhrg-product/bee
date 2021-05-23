package com.github.xhrg.bee.gateway.router;

import com.github.xhrg.bee.basic.bo.RouterBo;
import com.github.xhrg.bee.gateway.api.RequestContext;
import com.github.xhrg.bee.gateway.api.Router;
import com.github.xhrg.bee.gateway.http.HttpRequestExt;
import com.github.xhrg.bee.gateway.http.HttpResponseExt;
import org.springframework.stereotype.Component;

@Component
public class MySqlRouter implements Router {

    @Override
    public RouterBo init(RouterBo routerBo) {
        return null;
    }

    @Override
    public void doRouter(HttpRequestExt request, HttpResponseExt response, RequestContext requestContext) {

    }

    @Override
    public String name() {
        return "mysql";
    }
}
