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
public class RequestAdjustFilter implements PreFilter {

    private String queryParam = "query.";

    private String jsonBody = "body.";

    private String formBody = "form.";

    private String header = "header.";

    @Override
    public String name() {
        return "request_adjust_filter";
    }

    @Override
    public void init(FilterData filterData) {
        String configData = filterData.getData();
    }

    @Override
    public Flow doPreFilter(HttpRequestExt request, HttpResponseExt response, RequestContext requestContext) {
        return Flow.GO;
    }

    @Override
    public int sort() {
        return FilterSort.RequestAdjustFilter;
    }
}
