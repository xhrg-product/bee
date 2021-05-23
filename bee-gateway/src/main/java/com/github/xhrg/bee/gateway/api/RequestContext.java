package com.github.xhrg.bee.gateway.api;

import com.github.xhrg.bee.basic.bo.FilterBo;
import com.github.xhrg.bee.gateway.load.extbo.ApiRuntimeContext;
import com.github.xhrg.bee.gateway.http.HttpRequestExt;
import com.github.xhrg.bee.gateway.http.HttpResponseExt;
import io.netty.channel.Channel;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

//每一个http请求过来，都会创建一个新的RequestContext，这个RequestContext的生命周期，随着请求进入而生，请求返回而消失。
@Data
public class RequestContext {

    private Map<String, Object> map = new HashMap<>();

    private Channel channelFront;

    private Channel channelBack;

    private HttpRequestExt httpRequestExt;

    private HttpResponseExt httpResponseExt;

    //这个对象是根据数据库的接口配置有几个，进程则有几个对象。
    //ApiRuntimeContext和RequestContext的绑定关系是根据path匹配来的
    private ApiRuntimeContext apiRuntimeContext;

    //运行时候的过滤器对象
    private FilterBo filterBo;
}
