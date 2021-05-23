package com.github.xhrg.bee.gateway.router.bo;

import com.github.xhrg.bee.basic.bo.RouterBo;
import lombok.Data;
import org.apache.dubbo.rpc.service.GenericService;

@Data
public class DubboRouterBo extends RouterBo {

    private GenericService genericService;

    private String method;

    private String[] paramType;

}
