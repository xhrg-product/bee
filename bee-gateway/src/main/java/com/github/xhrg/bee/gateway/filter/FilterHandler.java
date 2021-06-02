package com.github.xhrg.bee.gateway.filter;

import com.github.xhrg.bee.gateway.api.*;
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

//过滤器控制类，主要有，缓存过滤器，执行过滤器，初始化过滤器
@Service
@Slf4j
public class FilterHandler implements BeanPostProcessor {

    private Map<String, PreFilter> preFilterMap = new HashMap<>();

    private Map<String, PostFilter> postFilterMap = new HashMap<>();

    public Flow pre(HttpRequestExt req, HttpResponseExt response, RequestContext requestContext) {
        List<FilterData> filterBoList = requestContext.getApiRuntimeContext().getPreFilter();
        for (FilterData filterData : filterBoList) {
            requestContext.setFilterData(filterData);
            Flow flow = filterData.getPreFilter().doPreFilter(req, response, requestContext);
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
            Flow flow = filterData.getPostFilter().doPostFilter(req, response, requestContext);
            if (Flow.isEnd(flow)) {
                return Flow.END;
            }
        }
        return Flow.GO;
    }

    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof PreFilter) {
            PreFilter preFilter = (PreFilter) bean;
            preFilterMap.put(preFilter.name(), preFilter);
        }
        if (bean instanceof PostFilter) {
            PostFilter postFilter = (PostFilter) bean;
            postFilterMap.put(postFilter.name(), postFilter);
        }
        return bean;
    }

    public boolean isPre(Filter filter) {
        return filter instanceof PreFilter;
    }

    public boolean isPost(Filter filter) {
        return filter instanceof PostFilter;
    }

    public Filter findFilter(String name) {
        Filter filter = preFilterMap.get(name);
        if (filter == null) {
            filter = postFilterMap.get(name);
        }
        return filter;
    }
}
