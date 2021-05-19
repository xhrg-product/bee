package com.github.xhrg.bee.gateway.api;

import java.util.HashMap;
import java.util.Map;

public class Context {

    private String channelId;

    private Map<String, Object> map = new HashMap<>();

    public void put(String key, Object value) {
        map.put(key, value);
    }

    public <T> T get(String key) {
        return map.get(key);
    }


    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }
}
