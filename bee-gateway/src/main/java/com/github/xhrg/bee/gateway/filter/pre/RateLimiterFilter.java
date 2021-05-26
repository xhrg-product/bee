package com.github.xhrg.bee.gateway.filter.pre;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.xhrg.bee.basic.bo.FilterBo;
import com.github.xhrg.bee.gateway.api.Filter;
import com.github.xhrg.bee.gateway.api.FilterType;
import com.github.xhrg.bee.gateway.api.Flow;
import com.github.xhrg.bee.gateway.api.RequestContext;
import com.github.xhrg.bee.gateway.http.HttpRequestExt;
import com.github.xhrg.bee.gateway.http.HttpResponseExt;
import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Bucket4j;
import io.github.bucket4j.local.LocalBucket;
import org.springframework.stereotype.Component;
import java.time.Duration;

@Component
public class RateLimiterFilter implements Filter {
    private static final String BUCKET_KEY = "bucket";
    private static final String HTTP_CODE = "httpCode";
    private static final String HTTP_BODY = "httpBody";
    @Override
    public String name() {
        return "rate_limiter";
    }
    @Override
    public FilterType type() {
        return FilterType.PRE;
    }
    @Override
    public FilterBo init(FilterBo filterBo) {
        JSONObject jsonObject = JSON.parseObject(filterBo.getData());
        Bandwidth limit = Bandwidth.simple(jsonObject.getInteger("timesOfSecond"), Duration.ofSeconds(1));
        Bucket bucket = Bucket4j.builder().addLimit(limit).build();
        filterBo.putMapExt(BUCKET_KEY, bucket);
        filterBo.putMapExt(HTTP_CODE, jsonObject.getIntValue(HTTP_CODE));
        filterBo.putMapExt(HTTP_BODY, jsonObject.getString(HTTP_BODY));
        return filterBo;
    }
    @Override
    public Flow doFilter(HttpRequestExt request, HttpResponseExt response, RequestContext requestContext) {
        FilterBo filterBo = requestContext.getFilterBo();
        LocalBucket localBucket = filterBo.getMapExtValue(BUCKET_KEY);
        boolean ok = localBucket.tryConsume(1);
        if (!ok) {
            response.setHttpCode(filterBo.getMapExtValue(HTTP_CODE));
            response.setBody(filterBo.getMapExtValue(HTTP_BODY));
            return Flow.END;
        }
        return Flow.GO;
    }
    @Override
    public int sort() {
        return 0;
    }
}
