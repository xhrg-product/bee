package com.github.xhrg.bee.gateway.caller;

import com.github.xhrg.bee.basic.bo.ApiRunBo;
import com.github.xhrg.bee.gateway.api.Context;
import com.github.xhrg.bee.gateway.filter.FilterService;
import com.github.xhrg.bee.gateway.http.HttpResponseExt;
import com.github.xhrg.bee.gateway.inner.InnerService;
import com.github.xhrg.bee.gateway.load.DataLoadService;
import com.github.xhrg.bee.gateway.router.RouterHandler;
import io.netty.handler.codec.http.FullHttpRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class Caller {

    @Autowired
    private RouterHandler routerHandler;

    @Autowired
    private FilterService filterService;

    @Autowired
    private DataLoadService dataLoadService;

    @Autowired
    private InnerService innerService;

    public boolean doCall(FullHttpRequest req, HttpResponseExt response, Context context) {
        String url = req.uri();

        boolean ok = innerService.doInner(url, response);
        if (!ok) {
            return false;
        }

        ApiRunBo apiRunBo = dataLoadService.match(url);
        if (apiRunBo == null) {
            return false;
        }
        context.setApiRunBo(apiRunBo);

        ok = filterService.pre(req, response, context);
        if (!ok) {
            return false;
        }

        if (Objects.equals(apiRunBo.getRouterBo().getType(), "http")) {
            routerHandler.route(req, response, context);
            return true;
        }
        return false;
    }
}
