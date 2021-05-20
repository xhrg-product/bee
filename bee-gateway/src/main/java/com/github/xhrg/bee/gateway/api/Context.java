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



    public FullHttpRequest getFullHttpRequest() {
        return fullHttpRequest;
    }

    public void setFullHttpRequest(FullHttpRequest fullHttpRequest) {
        this.fullHttpRequest = fullHttpRequest;
    }

    public Channel getChannelBack() {
        return channelBack;
    }

    public void setChannelBack(Channel channelBack) {
        this.channelBack = channelBack;
    }

    public void put(String key, Object value) {
        map.put(key, value);
    }

    public <T> T get(String key) {
        return (T) map.get(key);
    }

    public Channel getChannelFront() {
        return channelFront;
    }

    public void setChannelFront(Channel channelFront) {
        this.channelFront = channelFront;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }
}
