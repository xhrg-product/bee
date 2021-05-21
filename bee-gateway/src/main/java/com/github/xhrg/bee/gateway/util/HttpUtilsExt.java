package com.github.xhrg.bee.gateway.util;

import com.github.xhrg.bee.gateway.http.HttpResponseExt;

public abstract class HttpUtilsExt {

    public static HttpResponseExt error(HttpResponseExt httpResponseExt) {
        String message = "connection backend error, please check";
        httpResponseExt.setBody(message);
        httpResponseExt.setHttpCode(502);
        return httpResponseExt;
    }

    public static HttpResponseExt empty(HttpResponseExt httpResponseExt) {
        String message = "empty";
        httpResponseExt.setBody(message);
        httpResponseExt.setHttpCode(200);
        return httpResponseExt;
    }
}
