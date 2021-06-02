package com.github.xhrg.bee.gateway.filter.pre;

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
        return null;
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
    public int sort() {
        return 0;
    }

    public static void main(String[] args) {

        String id = "id";
        Entry entry = null;
        try {
            entry = SphU.entry(id, EntryType.IN);
            // Write your biz code here.
            // <<BIZ CODE>>
        } catch (Throwable t) {
            if (!BlockException.isBlockException(t)) {
                Tracer.trace(t);
            }
        } finally {
            if (entry != null) {
                entry.exit();
            }
        }
    }

    public static void main1(String[] args) throws Exception {
        CircuitBreakerConfig circuitBreakerConfig = CircuitBreakerConfig.custom()
                .failureRateThreshold(50)
                .waitDurationInOpenState(Duration.ofSeconds(5))
                .permittedNumberOfCallsInHalfOpenState(5)
                .slidingWindowSize(3)
                .build();
        CircuitBreakerRegistry circuitBreakerRegistry =
                CircuitBreakerRegistry.of(circuitBreakerConfig);
        CircuitBreaker circuitBreaker = circuitBreakerRegistry.circuitBreaker("name");
        while (true) {
            Thread.sleep(50);

            CheckedConsumer<Object> supplier = CircuitBreaker.decorateCheckedConsumer(circuitBreaker, new CheckedConsumer<Object>() {
                @Override
                public void accept(Object o) throws Throwable {
                    System.out.println(o);
                }

                @Override
                public CheckedConsumer<Object> andThen(CheckedConsumer<? super Object> after) {
                    return after;
                }

                @Override
                public Consumer<Object> unchecked() {
                    return null;
                }
            });

//            Supplier<Future<Object>> supplier = CircuitBreaker.decorateFuture(circuitBreaker, new Supplier<Future<Object>>() {
//                public Future<Object> get() {
//                    return new CompletableFuture();
//                }
//            });
//
//
//            Future<Object> future = supplier.get();
//            Supplier<Object> supplier = CircuitBreaker.decorateSupplier(circuitBreaker, new Supplier<Object>() {
//                @Override
//                public Object get() {
//                    System.out.println("触发业务代码------------------------------");
//                    int i = new Random().nextInt(10);
//                    if (i > 4) {
//                        i = 1 / 0;
//                    }
//                    //实际业务代码
//                    return "success";
//                }
//            });
            //这一步会让业务代码实际执行
//            Try<Object> trya = Try.ofSupplier(supplier);
//            trya.onFailure(new Consumer<Throwable>() {
//                public void accept(Throwable throwable) {
//                    System.out.println(throwable);
//                }
//            });
//            trya.onSuccess(new Consumer<Object>() {
//                @Override
//                public void accept(Object o) {
//                    System.out.println(o);
//                }
//            });
//
//            future.get();
        }
    }

    @Override
    public Flow doPostFilter(HttpRequestExt request, HttpResponseExt response, RequestContext requestContext) {
        return null;
    }
}
