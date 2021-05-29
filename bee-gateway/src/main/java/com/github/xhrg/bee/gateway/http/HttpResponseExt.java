package com.github.xhrg.bee.gateway.http;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;
import org.apache.commons.lang3.StringUtils;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

//这个类坚决不能使用lombok，不然属性会被修改。这个类本身是一个包装类，每个参数的读写都要特别注意。
public class HttpResponseExt {

    private String body = "";

    private int httpCode = 200;

    private Map<String, Object> headers = new HashMap<>();

    private FullHttpResponse fullHttpResponse;

    private boolean isReadData = false;

    private boolean isSetData = false;

    public FullHttpResponse full() {
        //给所有的response增加header
        //访问http接口,经常会遇到404,这个404是来自哪里的呢,有了这样的header就能看出来。
        headers.put("proxy_server", "bee_gateway");

        if (fullHttpResponse != null && !isReadData && !isSetData) {
            for (Map.Entry<String, Object> entry : headers.entrySet()) {
                fullHttpResponse.headers().add(entry.getKey(), entry.getValue());
            }
            return fullHttpResponse;
        }

        if (fullHttpResponse == null) {
            HttpResponseStatus httpResponseStatus = HttpResponseStatus.valueOf(this.httpCode);
            if (StringUtils.isEmpty(body)) {
                body = httpResponseStatus.reasonPhrase();
            }
            ByteBuf buf = Unpooled.copiedBuffer(body, CharsetUtil.UTF_8);
            FullHttpResponse fullHttpResponse = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, httpResponseStatus, buf);
            HttpUtil.setContentLength(fullHttpResponse, this.body.length());
            for (Map.Entry<String, Object> entry : headers.entrySet()) {
                fullHttpResponse.headers().add(entry.getKey(), entry.getValue());
            }
            return fullHttpResponse;
        }
        for (Map.Entry<String, Object> entry : headers.entrySet()) {
            fullHttpResponse.headers().add(entry.getKey(), entry.getValue());
        }

        ByteBuf byteBuf = Unpooled.copiedBuffer(body, CharsetUtil.UTF_8);
        return fullHttpResponse.replace(byteBuf);
    }

    public void addHeader(String key, Object value) {
        headers.put(key, value);
    }

    public String getBody() {
        if (isReadData) {
            return this.body;
        }
        body = fullHttpResponse.content().toString(StandardCharsets.UTF_8);
        isReadData = true;
        return body;
    }

    public void setBody(String body) {
        isSetData = true;
        isReadData = true;
        this.body = body;
    }

    public int getHttpCode() {
        return httpCode;
    }

    public void setHttpCode(int httpCode) {
        this.httpCode = httpCode;
    }

    public Map<String, Object> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, Object> headers) {
        this.headers = headers;
    }

    public FullHttpResponse getFullHttpResponse() {
        return fullHttpResponse;
    }

    public void setFullHttpResponse(FullHttpResponse fullHttpResponse) {
        this.fullHttpResponse = fullHttpResponse;
    }
}
