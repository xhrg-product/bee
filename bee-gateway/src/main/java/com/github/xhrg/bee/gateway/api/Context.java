package com.github.xhrg.bee.gateway.api;

import io.netty.channel.Channel;
import io.netty.handler.codec.http.FullHttpRequest;

import java.util.HashMap;
import java.util.Map;

public class Context {

    private String channelId;

    private Map<String, Object> map = new HashMap<>();

    private Channel channelFront;

    private Channel channelBack;

    private FullHttpRequest fullHttpRequest;

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
