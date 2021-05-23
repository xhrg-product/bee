package com.github.xhrg.bee.gateway.load.extbo;

import com.github.xhrg.bee.basic.bo.ApiBo;
import com.github.xhrg.bee.basic.bo.FilterBo;
import com.github.xhrg.bee.basic.bo.RouterBo;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

//比如说有100个接口，每个接口都有一个dubbo调用，那这个dubbo调用的句柄放在哪里呢，就是这个接口。
//该类是接口级别的运行时状态维护类，但是不是一个http请求的维护状态类，http请求的维护状态类是Context类
@Data
public class ApiRuntimeContext {

    private ApiBo apiBo;

    private RouterBo routerBo;

    private List<FilterBo> preFilter = new ArrayList<>();

    private List<FilterBo> postFilter = new ArrayList<>();
}
