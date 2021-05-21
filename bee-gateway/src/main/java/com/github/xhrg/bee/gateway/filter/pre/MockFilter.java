package com.github.xhrg.bee.gateway.filter.pre;

import com.github.xhrg.bee.gateway.api.Filter;
import com.github.xhrg.bee.gateway.api.FilterType;
import com.github.xhrg.bee.gateway.api.RequestContext;
import com.github.xhrg.bee.gateway.http.HttpRequestExt;
import com.github.xhrg.bee.gateway.http.HttpResponseExt;
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
    public boolean doFilter(HttpRequestExt request, HttpResponseExt response, RequestContext requestContext) {
        response.setBody("mock response");
        return false;
    }

    @Override
    public int sort() {
        return 0;
    }
}
