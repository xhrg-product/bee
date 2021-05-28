package com.github.xhrg.bee.gateway.router.data;

import lombok.Data;

@Data
public class HttpRouterData {

    private String host;

    private int port;

    private String targetUrl;

    private String targetPath;

}
