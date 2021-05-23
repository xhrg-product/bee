package com.github.xhrg.bee.gateway.router;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.xhrg.bee.basic.bo.RouterBo;
import com.github.xhrg.bee.gateway.router.bo.DubboRouterBo;
import com.github.xhrg.bee.gateway.api.RequestContext;
import com.github.xhrg.bee.gateway.api.Router;
import com.github.xhrg.bee.gateway.caller.Caller;
import com.github.xhrg.bee.gateway.http.HttpRequestExt;
import com.github.xhrg.bee.gateway.http.HttpResponseExt;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.rpc.model.ApplicationModel;
import org.apache.dubbo.rpc.service.GenericService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.CompletableFuture;

@Component
public class DubboRouter implements Router {

    @Resource
    private Caller caller;

    @Override
    public RouterBo init(RouterBo routerBo) {

        DubboRouterBo dubboRouterBo = new DubboRouterBo();

        JSONObject jsonObject = JSON.parseObject(routerBo.getData());
        RegistryConfig registryConfig = new RegistryConfig();
        registryConfig.setAddress(jsonObject.getString("zookeeper_addr"));
        ReferenceConfig<GenericService> reference = new ReferenceConfig<GenericService>();
        reference.setRegistry(registryConfig);
        ApplicationModel.getConfigManager().setApplication(new ApplicationConfig("bee-gateway"));
        reference.setInterface(jsonObject.getString("interface"));
        reference.setGeneric("true");
        reference.setAsync(true);
        GenericService genericService = reference.get();
        String method = jsonObject.getString("method");
        String[] paramType = new String[]{"java.lang.String"};

        dubboRouterBo.setMethod(method);
        dubboRouterBo.setParamType(paramType);
        dubboRouterBo.setGenericService(genericService);

        return dubboRouterBo;
    }

    @Override
    public void doRouter(HttpRequestExt request, HttpResponseExt response, RequestContext requestContext) {
        String body = request.getBody();
        DubboRouterBo dubboRouterBo = (DubboRouterBo) requestContext.getApiRuntimeContext().getRouterBo();
        CompletableFuture<Object> afuture = dubboRouterBo.getGenericService().$invokeAsync(dubboRouterBo.getMethod(),
                dubboRouterBo.getParamType(), new Object[]{body});
        afuture.whenComplete((value, throwable) -> {
            if (throwable != null) {
                response.setHttpCode(502);
                response.setBody("dubbo error, " + throwable.getMessage());
                caller.doPost(request, response, requestContext);
                return;
            }
            response.setBody(JSON.toJSONString(value));
            caller.doPost(request, response, requestContext);
        });
    }

    @Override
    public String name() {
        return "dubbo";
    }
}
