package com.github.xhrg.bee.gateway.netty.back;

import com.github.xhrg.bee.gateway.api.RequestContext;
import com.github.xhrg.bee.gateway.http.HttpRequestExt;
import com.github.xhrg.bee.gateway.netty.front.HttpFrontHandler;
import com.github.xhrg.bee.gateway.util.ChannelKey;
import com.github.xhrg.bee.gateway.util.HttpUtilsExt;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.*;
import io.netty.util.Attribute;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class NettyHttpClient {

    @Resource
    private HttpBackHandler httpBackHandler;

    @Resource
    private HttpFrontHandler httpFrontHandler;

    public void write(HttpRequestExt httpRequestExt, Channel channelFront, String host, int port, RequestContext requestContext) {

        Attribute<Channel> attr = channelFront.attr(ChannelKey.OTHER_CHANNEL);
        Channel channelBack = attr.get();
        if (channelBack != null) {
            httpBackHandler.writeToBack(channelBack, httpRequestExt);
            return;
        }
        Bootstrap cb = new Bootstrap().group(channelFront.eventLoop())
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<Channel>() {
                    public void initChannel(Channel ch) throws Exception {
                        ChannelPipeline pipe = ch.pipeline();
                        pipe.addLast("decoder", new HttpResponseDecoder());
                        pipe.addLast("encoder", new HttpRequestEncoder());
                        pipe.addLast("aggregator", new HttpObjectAggregator(10 * 1024 * 1024));
                        pipe.addLast(httpBackHandler);
                    }
                });
        try {
            ChannelFuture channelFuture = cb.connect(host, port);
            //这里因为要异步发送，所以需要做一些copy，
            //在netty的channelRead0中，当channelRead0结束后，会把请求对象回收掉，
            //如果这里不做一次copy，那么httpRequestExt中的实际FullHttpRequest和channelRead0中的句柄是一个。
            //当channelRead0结束后，我们就没办法使用这个对象了。
            channelFuture.addListener(future -> {
                //如果创建链接失败
                if (!future.isSuccess()) {
                    httpFrontHandler.writeToFront(channelFront, HttpUtilsExt.error(requestContext.getHttpResponseExt()));
                    return;
                }
                Channel channelNewBack = channelFuture.channel();

                //对向前和向后的channel进行一个双向绑定。
                channelFront.attr(ChannelKey.OTHER_CHANNEL).set(channelNewBack);
                channelNewBack.attr(ChannelKey.OTHER_CHANNEL).set(channelFront);

                httpBackHandler.writeToBack(channelNewBack, httpRequestExt);
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
