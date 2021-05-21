package com.github.xhrg.bee.gateway.netty.back;

import com.github.xhrg.bee.gateway.api.Context;
import com.github.xhrg.bee.gateway.router.HttpRouter;
import com.github.xhrg.bee.gateway.util.ChannelKey;
import com.github.xhrg.bee.gateway.util.ChannelUtils;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.FullHttpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@ChannelHandler.Sharable
@Component
public class HttpBackHandler extends SimpleChannelInboundHandler<FullHttpResponse> {

    @Autowired
    private HttpRouter httpRouter;

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        String id = ctx.channel().id().asLongText();
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FullHttpResponse response) throws Exception {
        Channel channelBack = ctx.channel();

        Context context = ChannelUtils.getContextByBackChannel(channelBack);
        context.setChannelBack(ctx.channel());

        //得到后台返回的响应，直接写会给前端
        Channel channelFront = channelBack.attr(ChannelKey.CHANNEL_FRONT_KEY).get();
        channelFront.writeAndFlush(response.retain());
    }
}
