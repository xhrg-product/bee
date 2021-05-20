package com.github.xhrg.bee.gateway.load;

import com.github.xhrg.bee.gateway.api.Filter;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FilterLoadService implements BeanPostProcessor {

    private List<Filter> filters = new ArrayList<>();

    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof Filter) {
            filters.add((Filter) bean);
        }
        return bean;
    }
}
