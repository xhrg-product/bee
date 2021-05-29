package com.github.xhrg.bee.gateway.filter;

import com.github.xhrg.bee.gateway.api.Filter;
import com.github.xhrg.bee.gateway.api.FilterType;
import com.github.xhrg.bee.gateway.api.Flow;
import com.github.xhrg.bee.gateway.api.RequestContext;
import com.github.xhrg.bee.gateway.exp.FilterException;
import com.github.xhrg.bee.gateway.http.HttpRequestExt;
import com.github.xhrg.bee.gateway.http.HttpResponseExt;
import com.github.xhrg.bee.gateway.load.data.FilterData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;

//过滤器控制类，主要有，缓存过滤器，执行过滤器，初始化过滤器
@Service
@Slf4j
public class FilterHandler implements BeanPostProcessor {

    private Map<String, Filter> filters = new HashMap<>();

    public Flow pre(HttpRequestExt req, HttpResponseExt response, RequestContext requestContext) {
        List<FilterData> filterBoList = requestContext.getApiRuntimeContext().getPreFilter();
        for (FilterData filterData : filterBoList) {
            requestContext.setFilterData(filterData);
            Flow flow = filterData.getFilter().doFilter(req, response, requestContext);
            if (Flow.isEnd(flow)) {
                return Flow.END;
            }
        }
        return Flow.GO;
    }

    public Flow post(HttpRequestExt req, HttpResponseExt response, RequestContext requestContext) {
        List<FilterData> filterBoList = requestContext.getApiRuntimeContext().getPostFilter();
        for (FilterData filterData : filterBoList) {
            requestContext.setFilterData(filterData);
            Flow flow = filterData.getFilter().doFilter(req, response, requestContext);
            if (Flow.isEnd(flow)) {
                return Flow.END;
            }
        }
        return Flow.GO;
    }

    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof Filter) {
            Filter filter = (Filter) bean;
            filters.put(filter.name(), filter);
        }
        return bean;
    }

    public Filter findFilter(String name) {
        return filters.get(name);
    }
}
