package com.github.xhrg.bee.gateway.filter.pre;

import com.github.xhrg.bee.gateway.api.Filter;
import com.github.xhrg.bee.gateway.api.FilterType;
import com.github.xhrg.bee.gateway.api.Flow;
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
import java.util.Random;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class CircuitBreakerFilter implements Filter {


    @Override
    public String name() {
        return null;
    }

    @Override
    public FilterType type() {
        return null;
    }

    @Override
    public void init(FilterData filterData) {

    }

    @Override
    public Flow doFilter(HttpRequestExt request, HttpResponseExt response, RequestContext requestContext) {
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
    public int sort() {
        return 0;
    }

    public static void main(String[] args) throws Exception {
        CircuitBreakerConfig circuitBreakerConfig = CircuitBreakerConfig.custom()
                .failureRateThreshold(50)
                .waitDurationInOpenState(Duration.ofSeconds(10))
                .permittedNumberOfCallsInHalfOpenState(1000)
                .slidingWindowSize(3)
                .build();
        CircuitBreakerRegistry circuitBreakerRegistry =
                CircuitBreakerRegistry.of(circuitBreakerConfig);
        CircuitBreaker circuitBreaker = circuitBreakerRegistry.circuitBreaker("name");
        while (true) {
            Thread.sleep(1000);
            Supplier<Object> supplier = CircuitBreaker.decorateSupplier(circuitBreaker, new Supplier<Object>() {
                @Override
                public Object get() {
                    System.out.println("触发业务代码------------------------------");
                    int i = new Random().nextInt(10);
                    if (i > 4) {
                        i = 1 / 0;
                    }
                    //实际业务代码
                    return "success";
                }
            });
            Try<Object> trya = Try.ofSupplier(supplier);


            trya.onFailure(new Consumer<Throwable>() {
                public void accept(Throwable throwable) {
                    System.out.println(throwable);
                }
            });
            trya.onSuccess(new Consumer<Object>() {
                @Override
                public void accept(Object o) {
                    System.out.println(o);
                }
            });
        }
    }
}
