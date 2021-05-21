package com.github.xhrg.bee.gateway.filter;

import com.github.xhrg.bee.basic.bo.FilterBo;
import com.github.xhrg.bee.gateway.api.Context;
import com.github.xhrg.bee.gateway.api.Filter;
import com.github.xhrg.bee.gateway.api.FilterType;
import com.github.xhrg.bee.gateway.http.HttpRequestExt;
import com.github.xhrg.bee.gateway.http.HttpResponseExt;
import io.netty.handler.codec.http.FullHttpRequest;
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

    public boolean pre(HttpRequestExt req, HttpResponseExt response, Context context) {
        FilterBo filterBo = context.getApiRuntimeContext().getFilterBo();
        Filter filter = filtersPre.get(filterBo.getName());
        //如果配置的过滤器，在本地过滤器没有找到，则跳过往下继续执行。
        if (filter == null) {
            return true;
        }
        return filter.doFilter(req, response, context);
    }

    public boolean post(HttpRequestExt req, HttpResponseExt response, Context context) {
        FilterBo filterBo = context.getApiRuntimeContext().getFilterBo();
        Filter filter = filtersPost.get(filterBo.getName());
        //如果配置的过滤器，在本地过滤器没有找到，则跳过往下继续执行。
        if (filter == null) {
            return true;
        }
        return filter.doFilter(req, response, context);
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
