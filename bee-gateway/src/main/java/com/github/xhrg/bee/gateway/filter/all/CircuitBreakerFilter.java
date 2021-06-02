package com.github.xhrg.bee.gateway.filter.all;

import com.alibaba.csp.sentinel.Entry;
import com.alibaba.csp.sentinel.EntryType;
import com.alibaba.csp.sentinel.SphU;
import com.alibaba.csp.sentinel.Tracer;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRule;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRuleManager;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.xhrg.bee.gateway.api.Flow;
import com.github.xhrg.bee.gateway.api.PostFilter;
import com.github.xhrg.bee.gateway.api.PreFilter;
import com.github.xhrg.bee.gateway.api.RequestContext;
import com.github.xhrg.bee.gateway.exp.FilterException;
import com.github.xhrg.bee.gateway.http.HttpRequestExt;
import com.github.xhrg.bee.gateway.http.HttpResponseExt;
import com.github.xhrg.bee.gateway.load.data.FilterData;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CircuitBreakerFilter implements PreFilter, PostFilter {

    private static final String HTTP_CODE = "httpCode";

    private static final String HTTP_BODY = "httpBody";

    private static final String KEY = "KEY";


    @Override
    public void init(FilterData filterData) {
        JSONObject jsonObject = JSON.parseObject(filterData.getData());
        filterData.putMapExt(HTTP_CODE, jsonObject.getString(HTTP_CODE));
        filterData.putMapExt(HTTP_BODY, jsonObject.getString(HTTP_BODY));

        String key = filterData.getApiId() + "";
        filterData.putMapExt(KEY, key);

        List<DegradeRule> rules = new ArrayList<>();
        DegradeRule rule = new DegradeRule();
        rule.setResource(key);
        // set limit exception ratio to 0.1
        // 将比例设置成0.6将全部通过, exception_ratio = 异常/通过量
        // 当资源的每秒异常总数占通过量的比值超过阈值（DegradeRule 中的 count）之后，资源进入降级状态
        rule.setCount(0.4);
        rule.setGrade(RuleConstant.DEGRADE_GRADE_EXCEPTION_RATIO);
        rule.setTimeWindow(10);
        rules.add(rule);
        DegradeRuleManager.loadRules(rules);
    }

    @Override
    public Flow doPreFilter(HttpRequestExt request, HttpResponseExt response, RequestContext requestContext) {
        Entry entry = null;
        try {
            entry = SphU.entry((String) requestContext.getFilterDataReader().getMapExtValue(KEY), EntryType.IN);
            requestContext.getMap().put("entry", entry);
        } catch (Throwable t) {
            //只要异常了，我就要返回，并且close
            if (!BlockException.isBlockException(t)) {
                Tracer.trace(t);
            }
            response.setBody(requestContext.getFilterDataReader().getMapExtValue(HTTP_BODY));
            response.setHttpCode(requestContext.getFilterDataReader().getMapExtValue(HTTP_CODE));
            if (entry != null) {
                entry.close();
            }
            return Flow.END;
        }
        return Flow.GO;
    }

    @Override
    public Flow doPostFilter(HttpRequestExt request, HttpResponseExt response, RequestContext requestContext) {
        Entry entry = (Entry) requestContext.getMap().get("entry");
        if (entry == null) {
            return Flow.GO;
        }
        if (response.getHttpCode() != 200) {
            Tracer.trace(new FilterException("for cc"));
        }
        entry.close();
        return Flow.GO;
    }

    @Override
    public int sort() {
        return 0;
    }

    @Override
    public String name() {
        return "circuit_breaker";
    }
}