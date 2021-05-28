package com.github.xhrg.bee.gateway.filter;

import com.github.xhrg.bee.basic.bo.FilterBo;
import com.github.xhrg.bee.gateway.api.Filter;
import com.github.xhrg.bee.gateway.api.FilterType;
import com.github.xhrg.bee.gateway.api.Flow;
import com.github.xhrg.bee.gateway.api.RequestContext;
import com.github.xhrg.bee.gateway.http.HttpRequestExt;
import com.github.xhrg.bee.gateway.http.HttpResponseExt;
import com.github.xhrg.bee.gateway.load.data.FilterData;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class FilterHandler implements BeanPostProcessor {

    private Map<String, Filter> filtersPre = new ConcurrentHashMap<>();

    private Map<String, Filter> filtersPost = new ConcurrentHashMap<>();

    private Map<Integer, String> sortMap = new TreeMap<>();

    public Flow pre(HttpRequestExt req, HttpResponseExt response, RequestContext requestContext) {
        List<FilterData> filterBoList = requestContext.getApiRuntimeContext().getPreFilter();
        for (FilterData filterData : filterBoList) {
            Filter filter = filtersPre.get(filterData.getName());
            //如果配置的过滤器，在本地过滤器没有找到，则跳过往下继续执行。
            if (filter != null) {
                requestContext.setFilterData(filterData);
                Flow flow = filter.doFilter(req, response, requestContext);
                if (Flow.isEnd(flow)) {
                    return Flow.END;
                }
            }
        }
        return Flow.GO;
    }

    public Flow post(HttpRequestExt req, HttpResponseExt response, RequestContext requestContext) {
        List<FilterData> filterBoList = requestContext.getApiRuntimeContext().getPostFilter();
        for (FilterData filterData : filterBoList) {
            Filter filter = filtersPost.get(filterData.getName());
            //如果配置的过滤器，在本地过滤器没有找到，则跳过往下继续执行。
            if (filter != null) {
                requestContext.setFilterData(filterData);
                Flow flow = filter.doFilter(req, response, requestContext);
                if (Flow.isEnd(flow)) {
                    return Flow.END;
                }
            }
        }
        return Flow.GO;
    }

    public boolean isPre(String filterName) {
        return filtersPre.containsKey(filterName);
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

    public void initFilterBo(FilterData filterData) {
        Filter filter = filtersPre.get(filterData.getName());
        if (filter == null) {
            filter = filtersPost.get(filterData.getName());
        }
        if (filter == null) {
            return;
        }
        filter.init(filterData);
    }
}
