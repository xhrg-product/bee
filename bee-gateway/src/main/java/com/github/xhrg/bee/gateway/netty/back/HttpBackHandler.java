package com.github.xhrg.bee.gateway.netty.back;

import com.github.xhrg.bee.gateway.api.RequestContext;
import com.github.xhrg.bee.gateway.caller.Caller;
import com.github.xhrg.bee.gateway.http.HttpRequestExt;
import com.github.xhrg.bee.gateway.util.ChannelKey;
import com.github.xhrg.bee.gateway.util.ChannelUtils;
import com.github.xhrg.bee.gateway.util.HttpUtilsExt;
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
        RequestContext requestContext = ChannelUtils.getContextByBackChannel(channelBack);
        requestContext.setChannelBack(ctx.channel());
        requestContext.getHttpResponseExt().setFullHttpResponse(response.copy());
        caller.doPost(requestContext.getHttpRequestExt(), requestContext.getHttpResponseExt(), requestContext);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        if (cause instanceof IOException) {
            this.closeChannel(ctx.channel());
            return;
        }
        super.exceptionCaught(ctx, cause);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        log.info("back链接关闭, channelInactive");
        this.closeChannel(ctx.channel());
        super.channelInactive(ctx);
    }

    public void writeToBack(Channel channelBack, HttpRequestExt httpRequestExt) {
        channelBack.writeAndFlush(httpRequestExt.full());
    }

    public void closeChannel(Channel channelBack) {
        Channel channelFront = channelBack.attr(ChannelKey.OTHER_CHANNEL).get();
        if (channelFront != null) {
            channelFront.writeAndFlush(HttpUtilsExt.emptyResponse());
            channelFront.close();
        }
        channelBack.close();
    }
}
