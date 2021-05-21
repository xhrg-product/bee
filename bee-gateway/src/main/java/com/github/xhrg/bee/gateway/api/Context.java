package com.github.xhrg.bee.gateway.api;

import com.github.xhrg.bee.basic.bo.ApiRuntimeContext;
import com.github.xhrg.bee.gateway.http.HttpRequestExt;
import com.github.xhrg.bee.gateway.http.HttpResponseExt;
import io.netty.channel.Channel;
import io.netty.handler.codec.http.FullHttpRequest;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class Context {

    private String channelId;

    private Map<String, Object> map = new HashMap<>();

    private Channel channelFront;

    private Channel channelBack;

    private HttpRequestExt httpRequestExt;

    private HttpResponseExt httpResponseExt;

    private ApiRuntimeContext apiRuntimeContext;
}
