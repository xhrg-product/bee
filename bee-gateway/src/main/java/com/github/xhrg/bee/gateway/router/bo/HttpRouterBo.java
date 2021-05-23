package com.github.xhrg.bee.gateway.router.bo;

import com.github.xhrg.bee.basic.bo.RouterBo;
import lombok.Data;

@Data
public class HttpRouterBo extends RouterBo {

    private String host;

    private int port;

    private String targetUrl;

    private String targetPath;

}
