package com.github.xhrg.bee.gateway.filter.pre;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.ValueFilter;
import com.github.xhrg.bee.gateway.api.Filter;
import com.github.xhrg.bee.gateway.api.FilterType;
import com.github.xhrg.bee.gateway.api.Flow;
import com.github.xhrg.bee.gateway.api.RequestContext;
import com.github.xhrg.bee.gateway.http.HttpRequestExt;
import com.github.xhrg.bee.gateway.http.HttpResponseExt;
import com.github.xhrg.bee.gateway.load.data.FilterData;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

//根据fastjson做参数转换
@Component
public class BodyChangeFilter implements Filter {

    private static final String JSON_OBJECT = "JSON_OBJECT";
    private static final String VALUE_FILTER = "VALUE_FILTER";

    private static final String BODY_KEY = "body.";
    private static final String HEADER_KEY = "header.";

    @Override

    public String name() {
        return "body_change";
    }

    @Override
    public FilterType type() {
        return FilterType.PRE;
    }

    @Override
    public void init(FilterData filterData) {
        String data = filterData.getData();
        JSONObject jsonObject = JSON.parseObject(data);
        filterData.putMapExt(JSON_OBJECT, jsonObject);
        filterData.putMapExt(VALUE_FILTER, new BodyChangeFilter.SimpleValueFilter());

    }

    @Override
    public Flow doFilter(HttpRequestExt request, HttpResponseExt response, RequestContext requestContext) {
        JSONObject jsonObject = requestContext.getFilterData().getMapExtValue(JSON_OBJECT);
        SimpleValueFilter valueFilter = requestContext.getFilterData().getMapExtValue(VALUE_FILTER);
        valueFilter.setHttpRequestExt(request);
        valueFilter.setJsonBody(null);
        String value = JSON.toJSONString(jsonObject, new BodyChangeFilter.SimpleValueFilter());
        request.setBody(value);
        return Flow.GO;
    }

    @Override
    public int sort() {
        return 0;
    }

    @Data
    static class SimpleValueFilter implements ValueFilter {
        public SimpleValueFilter() {
        }

        private HttpRequestExt httpRequestExt;
        private JSONObject jsonBody;

        public Object process(Object object, String name, Object value) {
            if (value == null || !(value instanceof String)) {
                return value;

            }
            String valueString = value.toString();
            if (valueString.startsWith(BODY_KEY)) {
                if (jsonBody == null) {
                    jsonBody = JSON.parseObject(httpRequestExt.getBody());
                }
                String key = StringUtils.remove(valueString, BODY_KEY);
                return jsonBody.get(key);
            }
            if (valueString.startsWith(HEADER_KEY)) {
                String key = StringUtils.remove(valueString, HEADER_KEY);
                String result = httpRequestExt.header(key);
                return result;
            }
            return value;
        }
    }
}
