package com.github.xhrg.bee.gateway.filter.pre;

import com.github.xhrg.bee.gateway.api.Filter;
import com.github.xhrg.bee.gateway.api.FilterType;
import com.github.xhrg.bee.gateway.api.Flow;
import com.github.xhrg.bee.gateway.api.RequestContext;
import com.github.xhrg.bee.gateway.http.HttpRequestExt;
import com.github.xhrg.bee.gateway.http.HttpResponseExt;
import com.github.xhrg.bee.gateway.load.data.FilterData;
import org.springframework.stereotype.Component;

@Component
public class CorsPreFilter implements Filter {
    @Override
    public String name() {
        return "cors_pre";
    }

    @Override
    public FilterType type() {
        return FilterType.PRE;
    }

    @Override
    public void init(FilterData filterData) {
    }

    @Override
    public Flow doFilter(HttpRequestExt request, HttpResponseExt response, RequestContext requestContext) {
        if ("option".equals(request.method())) {
            response.setBody("");
            response.setHttpCode(200);
            return Flow.END;
        }
        return Flow.GO;
    }

    @Override
    public int sort() {
        return 0;
    }
}
