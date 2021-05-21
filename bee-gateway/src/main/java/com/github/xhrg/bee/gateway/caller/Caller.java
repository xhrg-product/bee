package com.github.xhrg.bee.gateway.caller;

import com.github.xhrg.bee.basic.bo.ApiRuntimeContext;
import com.github.xhrg.bee.gateway.api.RequestContext;
import com.github.xhrg.bee.gateway.filter.FilterService;
import com.github.xhrg.bee.gateway.http.HttpRequestExt;
import com.github.xhrg.bee.gateway.http.HttpResponseExt;
import com.github.xhrg.bee.gateway.inner.InnerService;
import com.github.xhrg.bee.gateway.load.DataLoadService;
import com.github.xhrg.bee.gateway.router.RouterHandler;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Objects;

@Component
public class Caller {

    @Resource
    private RouterHandler routerHandler;

    @Resource
    private FilterService filterService;

    @Resource
    private DataLoadService dataLoadService;

    @Resource
    private InnerService innerService;

    public boolean doPost(HttpRequestExt req, HttpResponseExt response, RequestContext requestContext) {
        return filterService.post(req, response, requestContext);
    }

    public boolean doPre(HttpRequestExt req, HttpResponseExt response, RequestContext requestContext) {
        String url = req.uri();

        boolean ok = innerService.doInner(url, response);
        if (!ok) {
            return false;
        }

        ApiRuntimeContext apiRunBo = dataLoadService.match(url);
        if (apiRunBo == null) {
            return false;
        }
        requestContext.setApiRuntimeContext(apiRunBo);

        ok = filterService.pre(req, response, requestContext);
        if (!ok) {
            return false;
        }

        if (Objects.equals(apiRunBo.getRouterBo().getType(), "http")) {
            routerHandler.route(req, response, requestContext);
            return true;
        }
        return false;
    }
}
