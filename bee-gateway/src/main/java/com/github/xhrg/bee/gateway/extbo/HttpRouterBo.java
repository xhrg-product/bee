package com.github.xhrg.bee.gateway.extbo;

import com.alibaba.fastjson.JSON;
import com.github.xhrg.bee.basic.bo.RouterBo;
import lombok.Data;
import org.springframework.beans.BeanUtils;

@Data
public class HttpRouterBo extends RouterBo {

    private String host;

    private int port;

    private String TargetUrl;

    public static HttpRouterBo toMe(RouterBo routerBo) {
        HttpRouterBo httpRouterBo = JSON.parseObject(routerBo.getData(), HttpRouterBo.class);
        BeanUtils.copyProperties(routerBo, httpRouterBo);
        return httpRouterBo;
    }
}
