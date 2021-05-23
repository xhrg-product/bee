package com.github.xhrg.bee.gateway.http;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;
import org.apache.commons.lang3.StringUtils;

import java.nio.charset.StandardCharsets;

//这个类坚决不能使用lombok，不然属性会被修改。这个类本身是一个包装类，每个参数的读写都要特别注意。
public class HttpRequestExt {

    private FullHttpRequest fullHttpRequest;

    private String body;

    private boolean isReadData = false;

    private boolean isSetData = false;

    private String uri;

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public HttpRequestExt(FullHttpRequest fullHttpRequest) {
        this.fullHttpRequest = fullHttpRequest;
    }

    public boolean isReadData() {
        return isReadData;
    }

    public void setReadData(boolean readData) {
        isReadData = readData;
    }

    public boolean isSetData() {
        return isSetData;
    }

    public void setSetData(boolean setData) {
        isSetData = setData;
    }

    public void setBody(String data) {
        this.isSetData = true;
        this.isReadData = true;
        this.body = data;
    }

    public String uri() {
        return fullHttpRequest.uri();
    }

    public String getBody() {
        if (isReadData) {
            return body;
        }
        this.body = fullHttpRequest.content().toString(StandardCharsets.UTF_8);
        isReadData = true;
        return body;
    }

    public FullHttpRequest full() {
        if (StringUtils.isNotEmpty(this.uri)) {
            fullHttpRequest.setUri(this.uri);
        }
        //如果是写过数据，那么我需要重塑fullHttpRequest，刷新data。
        //如果是读过数据，也需要重塑fullHttpRequest，因为bodyData数据不支持读2次。
        //只要没读取，并且没写，才返回原始的数据。
        if (!isSetData && !isReadData) {
            return fullHttpRequest;
        }
        ByteBuf byteBuf = Unpooled.copiedBuffer(body, CharsetUtil.UTF_8);
        return fullHttpRequest.replace(byteBuf);
    }
}
