package com.github.xhrg.bee.gateway.filter;

import com.github.xhrg.bee.gateway.api.Context;
import com.github.xhrg.bee.gateway.api.Filter;
import com.github.xhrg.bee.gateway.api.FilterType;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import org.springframework.stereotype.Service;

@Service
public class MockFilter implements Filter {

    @Override
    public String name() {
        return "mockFilter";
    }

    @Override
    public FilterType type() {
        return FilterType.PRE;
    }

    @Override
    public boolean doFilter(FullHttpRequest request, FullHttpResponse response, Context context) {
        return false;
    }

    @Override
    public int sort() {
        return 0;
    }
}
