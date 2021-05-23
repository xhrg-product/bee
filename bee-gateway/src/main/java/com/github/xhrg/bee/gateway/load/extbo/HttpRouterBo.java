package com.github.xhrg.bee.gateway.load.extbo;

import com.alibaba.fastjson.JSON;
import com.github.xhrg.bee.basic.bo.RouterBo;
import com.github.xhrg.bee.gateway.exp.DataException;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.net.URL;

@Data
public class HttpRouterBo extends RouterBo {

    private String host;

    private int port;

    private String targetUrl;

    private String targetPath;

    public static HttpRouterBo toMe(RouterBo routerBo) {
        HttpRouterBo httpRouterBo = JSON.parseObject(routerBo.getData(), HttpRouterBo.class);
        BeanUtils.copyProperties(routerBo, httpRouterBo);
        try {
            URL url = new URL(httpRouterBo.getTargetUrl());
            httpRouterBo.setHost(url.getHost());
            httpRouterBo.setPort(url.getPort());
            httpRouterBo.setTargetPath(url.getPath());
        } catch (Exception e) {
            throw new DataException(e.getMessage());
        }
        return httpRouterBo;
    }
}
