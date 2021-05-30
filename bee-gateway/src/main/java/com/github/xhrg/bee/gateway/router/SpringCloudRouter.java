package com.github.xhrg.bee.gateway.router;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.xhrg.bee.gateway.api.RequestContext;
import com.github.xhrg.bee.gateway.api.Router;
import com.github.xhrg.bee.gateway.caller.Caller;
import com.github.xhrg.bee.gateway.http.HttpRequestExt;
import com.github.xhrg.bee.gateway.http.HttpResponseExt;
import com.github.xhrg.bee.gateway.load.data.RouterData;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

//TODO 需要改为异步执行
@Component
public class SpringCloudRouter implements Router {

    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Resource
    private RestTemplate restTemplate;

    @Resource
    private Caller caller;

    @Override
    public void init(RouterData routerData) {
        String data = routerData.getData();
        JSONObject jsonObject = JSON.parseObject(data);
        String service = jsonObject.getString("service");
        routerData.putMapExt("service", service);
    }

    @Override
    public void doRouter(HttpRequestExt request, HttpResponseExt response, RequestContext requestContext) {

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        String service = requestContext.getApiRuntimeContext().getRouterData().getMapExtValue("service");
        String path = request.getUri();
        String body = this.restTemplate.postForObject("http://" + service + path, map, String.class);
        response.setBody(body);
        caller.doPost(request, response, requestContext);
    }

    @Override
    public String name() {
        return "spring_cloud";
    }
}
