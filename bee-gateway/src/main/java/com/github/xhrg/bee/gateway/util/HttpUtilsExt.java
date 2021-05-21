package com.github.xhrg.bee.gateway.util;

import com.github.xhrg.bee.gateway.http.HttpResponseExt;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

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


    public static FullHttpResponse emptyResponse() {
        String message = "empty";
        ByteBuf byteBuf = Unpooled.copiedBuffer(message, CharsetUtil.UTF_8);
        FullHttpResponse response = new DefaultFullHttpResponse(
                HttpVersion.HTTP_1_1,
                HttpResponseStatus.BAD_GATEWAY, byteBuf);
        HttpUtil.setContentLength(response, message.length());
        return response;
    }
}
