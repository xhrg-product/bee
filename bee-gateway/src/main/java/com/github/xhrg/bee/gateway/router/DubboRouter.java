package com.github.xhrg.bee.gateway.router;

import com.alibaba.dubbo.remoting.exchange.ResponseCallback;
import com.alibaba.dubbo.remoting.exchange.ResponseFuture;
import com.github.xhrg.bee.gateway.api.Context;
import com.github.xhrg.bee.gateway.api.Router;
import com.github.xhrg.bee.gateway.http.HttpResponseExt;
import io.netty.handler.codec.http.FullHttpRequest;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.rpc.RpcContext;
import org.apache.dubbo.rpc.service.GenericService;

import java.util.HashMap;
import java.util.Map;

public class DubboRouter implements Router {

    @Override
    public void doRouter(FullHttpRequest request, HttpResponseExt response, Context context) {

        ReferenceConfig<GenericService> reference = new ReferenceConfig<GenericService>();
        reference.setInterface("com.xxx.XxxService");
        reference.setVersion("1.0.0");
        reference.setAsync(true);
        GenericService genericService = reference.get();
        Map<String, Object> person = new HashMap<String, Object>();
        person.put("name", "xxx");
        person.put("password", "yyy");
        genericService.$invoke("findPerson", new String[]
                {"com.xxx.Person"}, new Object[]{person});
        ResponseFuture responseFuture = (ResponseFuture) RpcContext.getContext().getFuture();
        responseFuture.setCallback(new ResponseCallback() {
            @Override
            public void done(Object response) {
            }

            @Override
            public void caught(Throwable exception) {
            }
        });
    }
}
