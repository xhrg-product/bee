package com.github.xhrg.bee.gateway.filter.post;

import com.github.xhrg.bee.gateway.api.Filter;
import com.github.xhrg.bee.gateway.api.FilterType;
import com.github.xhrg.bee.gateway.api.Flow;
import com.github.xhrg.bee.gateway.api.RequestContext;
import com.github.xhrg.bee.gateway.http.HttpRequestExt;
import com.github.xhrg.bee.gateway.http.HttpResponseExt;
import org.springframework.stereotype.Component;

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
        response.addHeader("filter_add_header", "value");
        return Flow.GO;
    }

    @Override
    public int sort() {
        return 0;
    }
}
