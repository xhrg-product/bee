package com.github.xhrg.bee.gateway.netty;

import com.github.xhrg.bee.gateway.api.Context;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

public abstract class NettyRequestHandler extends SimpleChannelInboundHandler<FullHttpRequest> {

    protected abstract void doReaderHttpRequest(FullHttpRequest req, FullHttpResponse response, Context context);

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
                Unpooled.copiedBuffer("", CharsetUtil.UTF_8));

        Context context = new Context();
        doReaderHttpRequest(req, response, context);
    }
}