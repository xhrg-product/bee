package com.github.xhrg.bee.gateway.netty.front;

import com.github.xhrg.bee.gateway.api.Flow;
import com.github.xhrg.bee.gateway.api.RequestContext;
import com.github.xhrg.bee.gateway.caller.Caller;
import com.github.xhrg.bee.gateway.http.HttpRequestExt;
import com.github.xhrg.bee.gateway.http.HttpResponseExt;
import com.github.xhrg.bee.gateway.util.ChannelKey;
import io.netty.channel.*;
import io.netty.handler.codec.http.*;
import io.netty.util.internal.PlatformDependent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.concurrent.ConcurrentMap;

@ChannelHandler.Sharable
@Component
@Slf4j
//ChannelRegisted --> ChannelActive --> ChannelInactive --> ChannelUnregistered
public class HttpFrontHandler extends SimpleChannelInboundHandler<FullHttpRequest> {

    //ChannelGroup提供了很多的功能，我们用不到，这里虽然使用了并发map，但是仅仅是在创建链接和销毁链接的时候用到，
    //正常的流程是用不到的，所以性能影响并不大。
    private final ConcurrentMap<ChannelId, Channel> allChannels = PlatformDependent.newConcurrentHashMap();

    private volatile boolean shutdown = false;

    @Resource
    private Caller caller;

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        allChannels.put(ctx.channel().id(), ctx.channel());
        super.channelActive(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        log.debug("channelInactive, 链接关闭, channelId is {}", ctx.channel().id().asLongText());
        allChannels.remove(ctx.channel().id(), ctx.channel());
        this.closeChannel(ctx.channel());
        super.channelInactive(ctx);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest request) throws Exception {

        log.debug("channelRead0, 请求进来了, channelId is {}", ctx.channel().id().asLongText());

        HttpResponseExt httpResponseExt = new HttpResponseExt();
        HttpRequestExt httpRequestExt = new HttpRequestExt(request.copy());
        RequestContext requestContext = new RequestContext();

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
        log.debug("exceptionCaught, 请求异常了, channelId is {}, Throwable is {}", ctx.channel().id().asLongText(), cause.getMessage());
        if (log.isDebugEnabled()) {
            log.debug("Throwable is, ", cause);
        }
        if (cause instanceof IOException) {
            this.closeChannel(ctx.channel());
            return;
        }
        super.exceptionCaught(ctx, cause);
    }

    public void writeToFront(Channel channelFront, HttpResponseExt httpResponseExt) {
        if (shutdown) {
            httpResponseExt.addHeader("Connection", "close");
        }
        channelFront.writeAndFlush(httpResponseExt.full());
    }

    public void closeChannel(Channel channelFront) {
        Channel channelBack = channelFront.attr(ChannelKey.OTHER_CHANNEL).get();
        if (channelBack != null) {
            channelBack.close();
        }
        channelFront.close();
    }

    public void shutdownAndWait() {
        shutdown = true;
        //这个n表示多少秒，测试我们使用100秒。
        int time = 100;
        //等待10秒
        while (allChannels.size() > 0 && ((time--) > 0)) {
            log.info("closeAndWait, allChannel size is {}, please wait", allChannels.size());
            try {
                Thread.sleep(1000);
            } catch (Exception e) {
            }
        }
        log.info("closeAndWait done, allChannel size is {}", allChannels.size());
    }
}
