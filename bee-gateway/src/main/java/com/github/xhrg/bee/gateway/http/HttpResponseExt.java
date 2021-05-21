package com.github.xhrg.bee.gateway.http;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

@Data
public class HttpResponseExt {

    private String body = "";

    private int httpCode = 200;

    private Map<String, Object> headers = new HashMap<>();

    public FullHttpResponse full() {

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

    public void addHeader(String key, Object value) {
        headers.put(key, value);
    }
}
