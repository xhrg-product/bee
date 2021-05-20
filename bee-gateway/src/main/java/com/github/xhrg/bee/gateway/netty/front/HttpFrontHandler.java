package com.github.xhrg.bee.gateway.netty.front;

import com.github.xhrg.bee.gateway.api.Context;
import com.github.xhrg.bee.gateway.cache.ContextCache;
import com.github.xhrg.bee.gateway.caller.Caller;
import com.github.xhrg.bee.gateway.router.HttpRouter;
import com.sun.tracing.dtrace.Attributes;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@ChannelHandler.Sharable
@Component
public class HttpFrontHandler extends SimpleChannelInboundHandler<FullHttpRequest> {

    @Autowired
    private HttpRouter httpRouter;

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
        //创建一个默认的FullHttpResponse
        FullHttpResponse response = new DefaultFullHttpResponse(
                HttpVersion.HTTP_1_1,
                HttpResponseStatus.OK,
                Unpooled.copiedBuffer("a", CharsetUtil.UTF_8));
        Context context = new Context();
        FullHttpRequest request = req.retain();
        context.setChannelFront(ctx.channel());
        context.setFullHttpRequest(request);
        contextCache.putContext(ctx.channel().id().asLongText(), context);
        this.doReaderHttpRequest(request, response, context);
    }


    public void doReaderHttpRequest(FullHttpRequest req, FullHttpResponse response, Context context) {
        String uri = req.uri();
        boolean ok = caller.doCall(req, response, context);
        if (!ok) {
            context.getChannelFront().writeAndFlush(response);
        }
    }
}
