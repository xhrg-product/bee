package com.github.xhrg.bee.gateway.netty.back;

import com.github.xhrg.bee.gateway.api.Context;
import com.github.xhrg.bee.gateway.cache.ChannelCache;
import com.github.xhrg.bee.gateway.cache.ContextCache;
import com.github.xhrg.bee.gateway.router.HttpRouter;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@ChannelHandler.Sharable
@Component
public class HttpBackHandler extends SimpleChannelInboundHandler<FullHttpResponse> {

    @Autowired
    private HttpRouter httpRouter;

    @Autowired
    private ChannelCache channelCache;

    @Autowired
    private ContextCache contextCache;

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        String id = ctx.channel().id().asLongText();
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FullHttpResponse response) throws Exception {
        String id = channelCache.getByBack(ctx.channel()).id().asLongText();
        Context context = contextCache.getContext(id);
        context.setChannelBack(ctx.channel());
        doReaderHttpRequest(context.getFullHttpRequest(), response, context);
    }

    public void doReaderHttpRequest(FullHttpRequest req, FullHttpResponse response, Context context) {
        Channel channel = channelCache.getByBack(context.getChannelBack());
        channel.writeAndFlush(response.retain());
    }
}
