package com.github.xhrg.bee.gateway.inner;

import com.github.xhrg.bee.gateway.http.HttpResponseExt;
import org.springframework.stereotype.Component;

@Component
public class InnerService {

    public boolean doInner(String url, HttpResponseExt response) {
        if ("/ping".equals(url)) {
            response.setBody("pong");
            return false;

        }
        if ("/favicon.ico".equals(url)) {
            response.setBody("ok");
            return false;
        }
        return true;
    }
}
