package com.github.xhrg.bee.gateway.netty.front;

import com.github.xhrg.bee.gateway.api.Context;
import com.github.xhrg.bee.gateway.cache.ContextCache;
import com.github.xhrg.bee.gateway.caller.Caller;
import com.github.xhrg.bee.gateway.http.HttpResponseExt;
import com.github.xhrg.bee.gateway.router.HttpRouter;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@ChannelHandler.Sharable
@Component
@Slf4j
public class HttpFrontHandler extends SimpleChannelInboundHandler<FullHttpRequest> {

    @Autowired
    private ContextCache contextCache;

    @Autowired
    private Caller caller;

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        String id = ctx.channel().id().asLongText();
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest req) throws Exception {
        Context context = new Context();
        FullHttpRequest request = req.retain();

        context.setChannelFront(ctx.channel());
        context.setFullHttpRequest(request);

        contextCache.putContext(ctx.channel(), context);

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
