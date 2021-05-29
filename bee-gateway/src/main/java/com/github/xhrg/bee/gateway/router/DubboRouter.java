package com.github.xhrg.bee.gateway.router;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.xhrg.bee.gateway.load.data.RouterData;
import com.github.xhrg.bee.gateway.router.data.DubboRouterData;
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
    public void init(RouterData routerData) {

        JSONObject jsonObject = JSON.parseObject(routerData.getData());
        //这一步也可以改为系统配置的唯一zookeeper地址
        String zookeeper = jsonObject.getString("zookeeper_addr");
        String inf = jsonObject.getString("interface");
        String method = jsonObject.getString("method");
        String returnType = jsonObject.getString("returnType");

        DubboRouterData dubboRouterData = new DubboRouterData();
        RegistryConfig registryConfig = new RegistryConfig();
        registryConfig.setAddress(zookeeper);

        ReferenceConfig<GenericService> reference = new ReferenceConfig<GenericService>();
        reference.setRegistry(registryConfig);
        ApplicationModel.getConfigManager().setApplication(new ApplicationConfig("bee-gateway"));
        reference.setInterface(inf);
        reference.setGeneric("true");
        reference.setAsync(true);
        GenericService genericService = reference.get();
        String[] paramType = new String[]{returnType};

        dubboRouterData.setMethod(method);
        dubboRouterData.setParamType(paramType);
        dubboRouterData.setGenericService(genericService);

        routerData.setDynaObject(dubboRouterData);
    }

    @Override
    public void doRouter(HttpRequestExt request, HttpResponseExt response, RequestContext requestContext) {
        String body = request.getBody();
        DubboRouterData dubboRouterData = requestContext.getApiRuntimeContext().getRouterData().getDynaObject();
        CompletableFuture<Object> future = dubboRouterData.getGenericService().$invokeAsync(dubboRouterData.getMethod(),
                dubboRouterData.getParamType(), new Object[]{body});
        future.whenComplete((value, throwable) -> {
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
