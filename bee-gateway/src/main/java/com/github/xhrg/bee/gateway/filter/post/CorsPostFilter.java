package com.github.xhrg.bee.gateway.filter.post;

import com.github.xhrg.bee.gateway.api.Filter;
import com.github.xhrg.bee.gateway.api.FilterType;
import com.github.xhrg.bee.gateway.api.Flow;
import com.github.xhrg.bee.gateway.api.RequestContext;
import com.github.xhrg.bee.gateway.http.HttpRequestExt;
import com.github.xhrg.bee.gateway.http.HttpResponseExt;
import com.github.xhrg.bee.gateway.load.data.FilterData;
import org.springframework.stereotype.Component;

@Component
public class CorsPostFilter implements Filter {
    @Override
    public String name() {
        return "cors_post";
    }

    @Override
    public FilterType type() {
        return FilterType.POST;
    }

    @Override
    public void init(FilterData filterData) {

    }

    @Override
    public Flow doFilter(HttpRequestExt request, HttpResponseExt response, RequestContext requestContext) {
        response.addHeader("Access-Control-Allow-Origin", "*");
        response.addHeader("Access-Control-Allow-Headers", "*");
        response.addHeader("Access-Control-Allow-Methods", "PUT,POST,GET,DELETE,OPTIONS");
        return Flow.GO;
    }

    @Override
    public int sort() {
        return 0;
    }
}
