package com.github.xhrg.bee.gateway.filter.pre;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.xhrg.bee.gateway.api.Flow;
import com.github.xhrg.bee.gateway.api.PreFilter;
import com.github.xhrg.bee.gateway.api.RequestContext;
import com.github.xhrg.bee.gateway.filter.FilterSort;
import com.github.xhrg.bee.gateway.http.HttpRequestExt;
import com.github.xhrg.bee.gateway.http.HttpResponseExt;
import com.github.xhrg.bee.gateway.load.data.FilterData;
import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Bucket4j;
import io.github.bucket4j.local.LocalBucket;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
public class RateLimiterFilter implements PreFilter {
    private static final String BUCKET_KEY = "bucket";
    private static final String HTTP_CODE = "httpCode";
    private static final String HTTP_BODY = "httpBody";

    @Override
    public String name() {
        return "rate_limiter";
    }

    @Override
    public void init(FilterData filterData) {
        JSONObject jsonObject = JSON.parseObject(filterData.getData());
        Bandwidth limit = Bandwidth.simple(jsonObject.getInteger("timesOfSecond"), Duration.ofSeconds(1));
        Bucket bucket = Bucket4j.builder().addLimit(limit).build();
        filterData.putMapExt(BUCKET_KEY, bucket);
        filterData.putMapExt(HTTP_CODE, jsonObject.getIntValue(HTTP_CODE));
        filterData.putMapExt(HTTP_BODY, jsonObject.getString(HTTP_BODY));
    }

    @Override
    public Flow doPreFilter(HttpRequestExt request, HttpResponseExt response, RequestContext requestContext) {
        LocalBucket localBucket = requestContext.getFilterDataReader().getMapExtValue(BUCKET_KEY);
        boolean ok = localBucket.tryConsume(1);
        if (!ok) {
            response.setHttpCode(requestContext.getFilterDataReader().getMapExtValue(HTTP_CODE));
            response.setBody(requestContext.getFilterDataReader().getMapExtValue(HTTP_BODY));
            return Flow.END;
        }
        return Flow.GO;
    }

    @Override
    public int sort() {
        return FilterSort.RateLimiterFilter;
    }
}
