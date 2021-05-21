package com.github.xhrg.bee.gateway.inner;

import com.github.xhrg.bee.gateway.api.Flow;
import com.github.xhrg.bee.gateway.http.HttpResponseExt;
import org.springframework.stereotype.Component;

@Component
public class InnerService {

    public Flow doInner(String url, HttpResponseExt response) {
        if ("/ping".equals(url)) {
            response.setBody("pong");
            return Flow.END;

        }
        if ("/favicon.ico".equals(url)) {
            response.setBody("ok");
            return Flow.END;
        }
        return Flow.GO;
    }
}
