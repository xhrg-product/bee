package com.github.xhrg.bee.gateway.api;

import com.github.xhrg.bee.basic.bo.ApiRunBo;
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

    private FullHttpRequest fullHttpRequest;

    private ApiRunBo apiRunBo;
}
