package com.github.xhrg.bee.gateway.caller;

import com.github.xhrg.bee.basic.bo.ApiRunBo;
import com.github.xhrg.bee.gateway.api.Context;
import com.github.xhrg.bee.gateway.filter.FilterService;
import com.github.xhrg.bee.gateway.load.DataLoadService;
import com.github.xhrg.bee.gateway.router.RouterHandler;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
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

    public boolean doCall(FullHttpRequest req, FullHttpResponse response, Context context) {
        String url = req.uri();
        ApiRunBo apiRunBo = dataLoadService.match(url);
        if (apiRunBo == null) {
            return false;
        }
        context.setApiRunBo(apiRunBo);

        boolean ok = filterService.pre(req, response, context);
        if (!ok) {
            return ok;
        }

        if (Objects.equals(apiRunBo.getRouterBo().getType(), "http")) {
            routerHandler.route(req, response, context);
            return true;
        }
        return false;
    }
}
