package com.github.xhrg.bee.gateway.cache;

import com.github.xhrg.bee.gateway.api.Context;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class ContextCache {

    private static Map<String, Context> map = new ConcurrentHashMap<>();

    public void putContext(String frontId, Context context) {
        map.put(frontId, context);
    }

    public Context getContext(String frontId) {
        return map.get(frontId);
    }
}
