package com.github.xhrg.bee.gateway.http;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

@Data
public class HttpResponseExt {

    private String body;

    private int httpCode;

    public FullHttpResponse full() {

        HttpResponseStatus httpResponseStatus = HttpResponseStatus.valueOf(this.httpCode);
        if (StringUtils.isEmpty(body)) {
            body = httpResponseStatus.reasonPhrase();
        }
        ByteBuf buf = Unpooled.copiedBuffer(body, CharsetUtil.UTF_8);
        FullHttpResponse fullHttpResponse = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, httpResponseStatus, buf);
        HttpUtil.setContentLength(fullHttpResponse, this.body.length());
        return fullHttpResponse;
    }
}
