package com.github.xhrg.bee.gateway.util;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

public abstract class HttpUtilsExt {

    public static FullHttpResponse CONNECTION_ERROR_RESPONSE = connectionErrorResponse();

    private static FullHttpResponse connectionErrorResponse() {
        String message = "connection backend error, please check";
        ByteBuf byteBuf = Unpooled.copiedBuffer(message, CharsetUtil.UTF_8);
        FullHttpResponse response = new DefaultFullHttpResponse(
                HttpVersion.HTTP_1_1,
                HttpResponseStatus.BAD_GATEWAY, byteBuf);
        HttpUtil.setContentLength(response, message.length());
        return response;
    }
}
