package com.github.xhrg.bee.gateway.filter.post;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.xhrg.bee.basic.bo.FilterBo;
import com.github.xhrg.bee.gateway.api.Filter;
import com.github.xhrg.bee.gateway.api.FilterType;
import com.github.xhrg.bee.gateway.api.Flow;
import com.github.xhrg.bee.gateway.api.RequestContext;
import com.github.xhrg.bee.gateway.http.HttpRequestExt;
import com.github.xhrg.bee.gateway.http.HttpResponseExt;
import org.springframework.stereotype.Component;

import java.util.HashMap;
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
    public FilterBo init(FilterBo filterBo) {
        Map<String, Object> map = new HashMap<>();
        JSONObject jsonObject = JSON.parseObject(filterBo.getData());
        for (Map.Entry<String, Object> e : jsonObject.entrySet()) {
            map.put(e.getKey(), e.getValue());
        }
        filterBo.setDataExt(map);
        return filterBo;
    }

    @Override
    public Flow doFilter(HttpRequestExt request, HttpResponseExt response, RequestContext context) {
        Map<String, Object> map = context.getFilterBo().getDataExt();
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            response.addHeader(entry.getKey(), entry.getValue());
        }
        return Flow.GO;
    }

    @Override
    public int sort() {
        return 0;
    }
}
