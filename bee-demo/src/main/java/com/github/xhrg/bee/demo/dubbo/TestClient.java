package com.github.xhrg.bee.demo.dubbo;

import com.alibaba.dubbo.remoting.exchange.ResponseCallback;
import com.alibaba.dubbo.remoting.exchange.ResponseFuture;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.rpc.RpcContext;
import org.apache.dubbo.rpc.protocol.dubbo.FutureAdapter;
import org.apache.dubbo.rpc.service.GenericService;

import java.util.HashMap;
import java.util.Map;

public class TestClient {

    public static void main(String[] args) {

        ApplicationConfig applicationConfig = new ApplicationConfig();

        RegistryConfig registryConfig = new RegistryConfig();
        registryConfig.setAddress("zookeeper://127.0.0.1:2181");
        ReferenceConfig<GenericService> reference = new ReferenceConfig<GenericService>();
        reference.setRegistry(registryConfig);
        reference.setApplication(new ApplicationConfig("test"));
        reference.setInterface("com.github.xhrg.bee.demo.dubbo.HelloService");
        reference.setGeneric("true");
        reference.setAsync(true);
        GenericService genericService = reference.get();
        Map<String, Object> person = new HashMap<String, Object>();
        person.put("name", "xxx");
        person.put("password", "yyy");
        genericService.$invoke("sayHello", new String[]{"java.long.String"}, new Object[]{"zhansan"});
        FutureAdapter responseFuture = (FutureAdapter) RpcContext.getContext().getFuture();
//        responseFuture.obtrudeException();
//        responseFuture.setCallback(new ResponseCallback() {
//            @Override
//            public void done(Object response) {
//                System.out.println("done");
//            }
//
//            @Override
//            public void caught(Throwable exception) {
//                System.out.println("caught");
//            }
//        });
    }
}
