package com.github.xhrg.bee.gateway.netty.front;

import com.github.xhrg.bee.gateway.api.Flow;
import com.github.xhrg.bee.gateway.api.RequestContext;
import com.github.xhrg.bee.gateway.caller.Caller;
import com.github.xhrg.bee.gateway.http.HttpRequestExt;
import com.github.xhrg.bee.gateway.http.HttpResponseExt;
import com.github.xhrg.bee.gateway.util.ChannelKey;
import com.github.xhrg.bee.gateway.util.ChannelUtils;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;

@ChannelHandler.Sharable
@Component
@Slf4j
//ChannelRegisted --> ChannelActive --> ChannelInactive --> ChannelUnregistered
public class HttpFrontHandler extends SimpleChannelInboundHandler<FullHttpRequest> {


    @Resource
    private Caller caller;

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        ChannelUtils.closeChannel(ctx.channel());
        super.channelInactive(ctx);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest req) throws Exception {
        RequestContext requestContext = new RequestContext();
        FullHttpRequest request = req.retain();

        HttpResponseExt httpResponseExt = new HttpResponseExt();
        HttpRequestExt httpRequestExt = new HttpRequestExt(request);

        requestContext.setChannelFront(ctx.channel());
        requestContext.setHttpRequestExt(httpRequestExt);
        requestContext.setHttpResponseExt(httpResponseExt);

        ctx.channel().attr(ChannelKey.CHANNEL_REQUEST_CONTEXT).set(requestContext);
        this.doReaderHttpRequest(httpRequestExt, httpResponseExt, requestContext);
    }

    public void doReaderHttpRequest(HttpRequestExt req, HttpResponseExt httpResponseExt, RequestContext requestContext) {
        Flow flow = caller.doPre(req, httpResponseExt, requestContext);
        if (Flow.isEnd(flow)) {
            requestContext.getChannelFront().writeAndFlush(httpResponseExt.full());
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.debug("FrontChannel Handler exceptionCaught");
        if (cause instanceof IOException) {
            ChannelUtils.closeChannel(ctx.channel());
            return;
        }
        super.exceptionCaught(ctx, cause);
    }
}
