package com.github.xhrg.bee.gateway.extbo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.xhrg.bee.basic.bo.RouterBo;
import lombok.Data;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.rpc.model.ApplicationModel;
import org.apache.dubbo.rpc.service.GenericService;

@Data
public class DubboRouterBo extends RouterBo {

    private GenericService genericService;

    private String method;

    private String[] paramType;

    public DubboRouterBo(String data) {
        JSONObject jsonObject = JSON.parseObject(data);
        RegistryConfig registryConfig = new RegistryConfig();
        registryConfig.setAddress(jsonObject.getString("zookeeper_addr"));
        ReferenceConfig<GenericService> reference = new ReferenceConfig<GenericService>();
        reference.setRegistry(registryConfig);
        ApplicationModel.getConfigManager().setApplication(new ApplicationConfig("bee-gateway"));
        reference.setInterface(jsonObject.getString("interface"));
        reference.setGeneric("true");
        reference.setAsync(true);
        GenericService genericService = reference.get();
        method = jsonObject.getString("method");
        paramType = new String[]{"java.lang.String"};
    }
}
