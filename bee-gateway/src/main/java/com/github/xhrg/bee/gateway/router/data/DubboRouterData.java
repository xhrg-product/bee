package com.github.xhrg.bee.gateway.router.data;

import lombok.Data;
import org.apache.dubbo.rpc.service.GenericService;

@Data
public class DubboRouterData {

    private GenericService genericService;

    private String method;

    private String[] paramType;

}
