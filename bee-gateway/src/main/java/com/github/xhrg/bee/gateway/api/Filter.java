package com.github.xhrg.bee.gateway.api;

import com.github.xhrg.bee.basic.bo.FilterBo;
import com.github.xhrg.bee.basic.bo.RouterBo;
import com.github.xhrg.bee.gateway.http.HttpRequestExt;
import com.github.xhrg.bee.gateway.http.HttpResponseExt;

public interface Filter {

    //返回接口名称，这个名称一般是mock_filter,add_header会和数据库的name对应。
    String name();

    //类型PRE表示前置插件，POST不是http的method，而是后置处理器的意思
    FilterType type();

    //初始化，参数是FilterBo，可以根据FilterBo继承一个子类，然后在doFilter获取到。
    //注意，该接口绝对不能返回null
    FilterBo init(FilterBo filterBo);

    //执行过滤器
    Flow doFilter(HttpRequestExt request,
                  HttpResponseExt response, RequestContext requestContext);

    //过滤器的排序值
    int sort();
}
