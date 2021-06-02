package com.github.xhrg.bee.gateway.api;

import com.github.xhrg.bee.gateway.load.data.FilterData;

public interface Filter {

    //返回接口名称，这个名称一般是mock_filter,add_header会和数据库的name对应。
    String name();

    //初始化，参数是FilterBo，可以根据FilterBo继承一个子类，然后在doFilter获取到。
    //注意，该接口绝对不能返回null
    void init(FilterData filterData);

    //过滤器的排序值
    int sort();
}
