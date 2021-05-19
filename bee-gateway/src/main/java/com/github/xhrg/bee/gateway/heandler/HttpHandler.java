package com.github.xhrg.bee.gateway.heandler;

import com.github.xhrg.bee.netty.NettyRequestHandler;
import io.netty.channel.Channel;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;

public class HttpHandler extends NettyRequestHandler {

    public void doReaderHttpRequest(FullHttpRequest req, FullHttpResponse response, Channel channel) {
        String uri = req.uri();



    }
}
