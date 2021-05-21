package com.github.xhrg.bee.gateway.filter.pre;

import com.github.xhrg.bee.gateway.api.Context;
import com.github.xhrg.bee.gateway.api.Filter;
import com.github.xhrg.bee.gateway.api.FilterType;
import com.github.xhrg.bee.gateway.http.HttpResponseExt;
import io.netty.handler.codec.http.FullHttpRequest;
import org.springframework.stereotype.Service;

@Service
public class MockFilter implements Filter {

    @Override
    public String name() {
        return "mock_filter";
    }

    @Override
    public FilterType type() {
        return FilterType.PRE;
    }

    @Override
    public boolean doFilter(FullHttpRequest request, HttpResponseExt response, Context context) {
        response.setBody("mock response");
        return false;
    }

    @Override
    public int sort() {
        return 0;
    }
}