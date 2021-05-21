package com.github.xhrg.bee.gateway.netty.back;

import com.github.xhrg.bee.gateway.api.Context;
import com.github.xhrg.bee.gateway.caller.Caller;
import com.github.xhrg.bee.gateway.util.ChannelKey;
import com.github.xhrg.bee.gateway.util.ChannelUtils;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.FullHttpResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;

@ChannelHandler.Sharable
@Component
@Slf4j
public class HttpBackHandler extends SimpleChannelInboundHandler<FullHttpResponse> {

    @Resource
    private Caller caller;

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FullHttpResponse response) throws Exception {
        Channel channelBack = ctx.channel();

        Context context = ChannelUtils.getContextByBackChannel(channelBack);
        context.setChannelBack(ctx.channel());

        caller.doPost(context.getHttpRequestExt(), context.getHttpResponseExt(), context);

        //得到后台返回的响应，直接写会给前端
        Channel channelFront = channelBack.attr(ChannelKey.OTHER_CHANNEL).get();
        channelFront.writeAndFlush(context.getHttpResponseExt().full());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        if (cause instanceof IOException) {
            ChannelUtils.closeChannel(ctx.channel());
            return;
        }
        super.exceptionCaught(ctx, cause);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        log.info("back链接关闭, channelInactive");
        ChannelUtils.closeChannel(ctx.channel());
        super.channelInactive(ctx);
    }
}
