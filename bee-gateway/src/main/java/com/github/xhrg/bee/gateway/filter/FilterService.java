package com.github.xhrg.bee.gateway.filter;

import com.github.xhrg.bee.basic.bo.FilterBo;
import com.github.xhrg.bee.gateway.api.Filter;
import com.github.xhrg.bee.gateway.api.FilterType;
import com.github.xhrg.bee.gateway.api.Flow;
import com.github.xhrg.bee.gateway.api.RequestContext;
import com.github.xhrg.bee.gateway.http.HttpRequestExt;
import com.github.xhrg.bee.gateway.http.HttpResponseExt;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class FilterService implements BeanPostProcessor {

    private Map<String, Filter> filtersPre = new ConcurrentHashMap<>();

    private Map<String, Filter> filtersPost = new ConcurrentHashMap<>();

    private Map<Integer, String> sortMap = new TreeMap<>();

    public Flow pre(HttpRequestExt req, HttpResponseExt response, RequestContext requestContext) {
        FilterBo filterBo = requestContext.getApiRuntimeContext().getFilterBo();
        Filter filter = filtersPre.get(filterBo.getName());
        //如果配置的过滤器，在本地过滤器没有找到，则跳过往下继续执行。
        if (filter == null) {
            return Flow.GO;
        }
        return filter.doFilter(req, response, requestContext);
    }

    public Flow post(HttpRequestExt req, HttpResponseExt response, RequestContext requestContext) {
        FilterBo filterBo = requestContext.getApiRuntimeContext().getFilterBo();
        Filter filter = filtersPost.get(filterBo.getName());
        //如果配置的过滤器，在本地过滤器没有找到，则跳过往下继续执行。
        if (filter == null) {
            return Flow.GO;
        }
        return filter.doFilter(req, response, requestContext);
    }

    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof Filter) {
            Filter filter = (Filter) bean;
            sortMap.put(filter.sort(), filter.name());
            if (filter.type() == FilterType.PRE) {
                filtersPre.put(filter.name(), filter);
            } else {
                filtersPost.put(filter.name(), filter);
            }
        }
        return bean;
    }
}
