package com.github.xhrg.bee.gateway.http;

import io.netty.handler.codec.http.FullHttpRequest;
import lombok.Data;

@Data
public class HttpRequestExt {

    private FullHttpRequest fullHttpRequest;

    public HttpRequestExt(FullHttpRequest fullHttpRequest) {
        this.fullHttpRequest = fullHttpRequest;
    }

    public String getData() {
        return "";
    }

    public void setUri(String path) {
    }

    public FullHttpRequest full() {
        return null;
    }

    public String uri() {
        return "";
    }
}
