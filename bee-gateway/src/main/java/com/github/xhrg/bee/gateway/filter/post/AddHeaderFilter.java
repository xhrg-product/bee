package com.github.xhrg.bee.gateway.filter.post;

import com.github.xhrg.bee.gateway.api.Context;
import com.github.xhrg.bee.gateway.api.Filter;
import com.github.xhrg.bee.gateway.api.FilterType;
import com.github.xhrg.bee.gateway.http.HttpResponseExt;
import io.netty.handler.codec.http.FullHttpRequest;
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
    public boolean doFilter(FullHttpRequest request, HttpResponseExt response, Context context) {
        response.addHeader("filter_add_header", "value");
        return true;
    }

    @Override
    public int sort() {
        return 0;
    }
}
