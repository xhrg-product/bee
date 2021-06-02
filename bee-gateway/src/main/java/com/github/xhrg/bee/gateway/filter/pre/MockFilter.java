package com.github.xhrg.bee.gateway.filter.pre;

import com.github.xhrg.bee.gateway.api.Flow;
import com.github.xhrg.bee.gateway.api.PreFilter;
import com.github.xhrg.bee.gateway.api.RequestContext;
import com.github.xhrg.bee.gateway.http.HttpRequestExt;
import com.github.xhrg.bee.gateway.http.HttpResponseExt;
import com.github.xhrg.bee.gateway.load.data.FilterData;
import org.springframework.stereotype.Service;

@Service
public class MockFilter implements PreFilter {

    @Override
    public String name() {
        return "mock_filter";
    }

    @Override
    public void init(FilterData filterBo) {
    }

    @Override
    public Flow doPreFilter(HttpRequestExt request, HttpResponseExt response, RequestContext requestContext) {
        response.setBody(requestContext.getFilterDataReader().getData());
        return Flow.END;
    }

    @Override
    public int sort() {
        return 0;
    }
}
