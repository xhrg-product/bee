package com.github.xhrg.bee.gateway.filter.post;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.xhrg.bee.gateway.api.Filter;
import com.github.xhrg.bee.gateway.api.FilterType;
import com.github.xhrg.bee.gateway.api.Flow;
import com.github.xhrg.bee.gateway.api.RequestContext;
import com.github.xhrg.bee.gateway.http.HttpRequestExt;
import com.github.xhrg.bee.gateway.http.HttpResponseExt;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class AddHeaderFilter implements Filter {
    @Override
    public String name() {
        return "add_header";
    }

    @Override
    public FilterType type() {
        return FilterType.POST;
    }

    @Override
    public Flow doFilter(HttpRequestExt request, HttpResponseExt response, RequestContext context) {
        String headers = context.getFilterBo().getData();
        JSONObject jsonObject = JSON.parseObject(headers);
        for (Map.Entry<String, Object> e : jsonObject.entrySet()) {
            response.addHeader(e.getKey(), e.getValue());
        }
        return Flow.GO;
    }

    @Override
    public int sort() {
        return 0;
    }
}
