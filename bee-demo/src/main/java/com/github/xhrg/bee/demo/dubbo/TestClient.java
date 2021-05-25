package com.github.xhrg.bee.demo.dubbo;

import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.rpc.model.ApplicationModel;
import org.apache.dubbo.rpc.service.GenericService;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class TestClient {

    public static void main(String[] args)
//throws ExecutionException, InterruptedException
    {

//        RegistryConfig registryConfig = new RegistryConfig();
//        registryConfig.setAddress("zookeeper://127.0.0.1:2181");
//        ReferenceConfig<GenericService> reference = new ReferenceConfig<GenericService>();
//        reference.setRegistry(registryConfig);
//        ApplicationModel.getConfigManager().setApplication(new ApplicationConfig("bee-gateway"));
//        reference.setInterface("com.github.xhrg.bee.demo.dubbo.HelloService");
//        reference.setGeneric("true");
//        reference.setAsync(true);
//        GenericService genericService = reference.get();
//        CompletableFuture<Object> afuture = genericService.$invokeAsync("sayHello", new String[]{"java.lang.String"}, new Object[]{"zhansan"});
//        afuture.whenComplete((value, throwable) -> {
//            System.out.println("w");
//            System.out.println(value);
//            System.out.println(throwable);
//        });
    }
}
