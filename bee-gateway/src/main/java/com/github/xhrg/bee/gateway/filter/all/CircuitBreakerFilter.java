package com.github.xhrg.bee.gateway.filter.all;

import com.alibaba.csp.sentinel.Entry;
import com.alibaba.csp.sentinel.EntryType;
import com.alibaba.csp.sentinel.SphU;
import com.alibaba.csp.sentinel.Tracer;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.github.xhrg.bee.gateway.api.Flow;
import com.github.xhrg.bee.gateway.api.PostFilter;
import com.github.xhrg.bee.gateway.api.PreFilter;
import com.github.xhrg.bee.gateway.api.RequestContext;
import com.github.xhrg.bee.gateway.exp.BadException;
import com.github.xhrg.bee.gateway.http.HttpRequestExt;
import com.github.xhrg.bee.gateway.http.HttpResponseExt;
import com.github.xhrg.bee.gateway.load.data.FilterData;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.vavr.CheckedConsumer;
import io.vavr.control.Try;

import java.time.Duration;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class CircuitBreakerFilter implements PreFilter, PostFilter {

    @Override
    public String name() {
        return "circuit_breaker";
    }

    @Override
    public void init(FilterData filterData) {

    }

    @Override
    public Flow doPreFilter(HttpRequestExt request, HttpResponseExt response, RequestContext requestContext) {
        // 为断路器创建自定义的配置
        CircuitBreakerConfig circuitBreakerConfig = CircuitBreakerConfig.custom()
                .failureRateThreshold(50)
                .waitDurationInOpenState(Duration.ofMillis(1000))
                .permittedNumberOfCallsInHalfOpenState(2)
                .slidingWindowSize(2)
                .build();
        CircuitBreakerRegistry circuitBreakerRegistry =
                CircuitBreakerRegistry.of(circuitBreakerConfig);
        CircuitBreaker circuitBreaker = circuitBreakerRegistry.circuitBreaker("name");
        Supplier<Object> supplier = CircuitBreaker.decorateSupplier(circuitBreaker, new Supplier<Object>() {
            @Override
            public Object get() {
                //实际业务代码
                return null;
            }
        });
        Try.ofSupplier(supplier).recover(BadException.class, new Object()).get();
        return Flow.GO;
    }

    @Override
    public Flow doPostFilter(HttpRequestExt request, HttpResponseExt response, RequestContext requestContext) {
        return null;
    }

    @Override
    public int sort() {
        return 0;
    }
}
