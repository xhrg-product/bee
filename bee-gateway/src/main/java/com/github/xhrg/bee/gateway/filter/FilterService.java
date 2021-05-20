package com.github.xhrg.bee.gateway.filter;

import com.github.xhrg.bee.basic.bo.FilterBo;
import com.github.xhrg.bee.gateway.api.Context;
import com.github.xhrg.bee.gateway.api.Filter;
import com.github.xhrg.bee.gateway.api.FilterType;
import com.github.xhrg.bee.gateway.http.HttpResponseExt;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class FilterService implements BeanPostProcessor {

    private Map<String, Filter> filterMapPre = new ConcurrentHashMap<>();

    private Map<String, Filter> filterMapPost = new ConcurrentHashMap<>();

    private Map<Integer, String> sortMap = new TreeMap<>();

    public boolean pre(FullHttpRequest req, HttpResponseExt response, Context context) {
        FilterBo filterBo = context.getApiRunBo().getFilterBo();
        Filter filter = filterMapPre.get(filterBo.getName());
        return filter.doFilter(req, response, context);
    }

    public boolean post(FullHttpRequest req, HttpResponseExt response, Context context) {
        FilterBo filterBo = context.getApiRunBo().getFilterBo();
        Filter filter = filterMapPost.get(filterBo.getName());
        return filter.doFilter(req, response, context);
    }


    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        System.out.println(bean.getClass());
        if (bean instanceof Filter) {
            Filter filter = (Filter) bean;
            sortMap.put(filter.sort(), filter.name());
            if (filter.type() == FilterType.PRE) {
                filterMapPre.put(filter.name(), filter);
            } else {
                filterMapPost.put(filter.name(), filter);
            }
        }
        return bean;
    }


}
