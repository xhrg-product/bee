package com.github.xhrg.bee.gateway.api;

//在http请求进来后，会有大量的过滤器，这个过滤器执行结束后，是应该返回给前端,还是继续下一个过滤器呢？
//go表示继续往下走
//end表示结束了，http可以返回了
public enum Flow {
    GO, //继续往下走
    END;//结束返回前端

    public static boolean isEnd(Flow flow) {
        return flow == END;
    }
}
