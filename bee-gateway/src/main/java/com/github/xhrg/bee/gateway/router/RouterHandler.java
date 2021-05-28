package com.github.xhrg.bee.gateway.router;

import com.github.xhrg.bee.gateway.api.RequestContext;
import com.github.xhrg.bee.gateway.api.Router;
import com.github.xhrg.bee.gateway.http.HttpRequestExt;
import com.github.xhrg.bee.gateway.http.HttpResponseExt;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class RouterHandler implements BeanPostProcessor {

    private Map<String, Router> mapRouter = new HashMap<>();

    public void route(HttpRequestExt req, HttpResponseExt response, RequestContext requestContext) {
        Router router = mapRouter.get(requestContext.getApiRuntimeContext().getRouterData().getName());
        router.doRouter(req, response, requestContext);
    }

    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof Router) {
            Router router = (Router) bean;
            mapRouter.put(router.name(), router);
        }
        return bean;
    }

    public Router findRouter(String name) {
        return mapRouter.get(name);
    }
}
