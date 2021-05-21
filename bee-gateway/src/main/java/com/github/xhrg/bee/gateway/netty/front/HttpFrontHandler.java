package com.github.xhrg.bee.gateway.netty.front;

import com.github.xhrg.bee.gateway.api.Context;
import com.github.xhrg.bee.gateway.caller.Caller;
import com.github.xhrg.bee.gateway.http.HttpResponseExt;
import com.github.xhrg.bee.gateway.util.ChannelKey;
import com.github.xhrg.bee.gateway.util.ChannelUtils;
import com.github.xhrg.bee.gateway.util.HttpUtilsExt;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

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
        Context context = new Context();
        FullHttpRequest request = req.retain();

        context.setChannelFront(ctx.channel());
        context.setFullHttpRequest(request);

        ctx.channel().attr(ChannelKey.CHANNEL_CONTEXT_KEY).set(context);

        HttpResponseExt httpResponseExt = new HttpResponseExt();
        this.doReaderHttpRequest(request, httpResponseExt, context);
    }

    public void doReaderHttpRequest(FullHttpRequest req, HttpResponseExt httpResponseExt, Context context) {
        String uri = req.uri();
        log.info(uri);
        boolean ok = caller.doCall(req, httpResponseExt, context);
        if (!ok) {
            context.getChannelFront().writeAndFlush(httpResponseExt.full());
        }
    }
}
