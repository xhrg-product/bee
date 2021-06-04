package com.github.xhrg.bee.gateway.filter.pre;

import com.github.xhrg.bee.gateway.api.Flow;
import com.github.xhrg.bee.gateway.api.PreFilter;
import com.github.xhrg.bee.gateway.api.RequestContext;
import com.github.xhrg.bee.gateway.filter.FilterSort;
import com.github.xhrg.bee.gateway.http.HttpRequestExt;
import com.github.xhrg.bee.gateway.http.HttpResponseExt;
import com.github.xhrg.bee.gateway.load.data.FilterData;
import org.springframework.stereotype.Component;

@Component
public class CorsFilter implements PreFilter {

    @Override
    public String name() {
        return "cors_post";
    }

    @Override
    public void init(FilterData filterData) {
    }

    @Override
    public Flow doPreFilter(HttpRequestExt request, HttpResponseExt response, RequestContext requestContext) {
        if ("option".equals(request.method())) {
            response.setBody("");
            response.setHttpCode(200);
            return Flow.END;
        }
        response.addHeader("Access-Control-Allow-Origin", "*");
        response.addHeader("Access-Control-Allow-Headers", "*");
        response.addHeader("Access-Control-Allow-Methods", "PUT,POST,GET,DELETE,OPTIONS");
        return Flow.GO;
    }

    @Override
    public int sort() {
        return FilterSort.CorsFilter;
    }
}
