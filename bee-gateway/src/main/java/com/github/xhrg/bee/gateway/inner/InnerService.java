package com.github.xhrg.bee.gateway.inner;

import com.github.xhrg.bee.gateway.api.Flow;
import com.github.xhrg.bee.gateway.http.HttpResponseExt;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.DefaultHttpResponse;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import javax.annotation.PostConstruct;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

@Component
@Slf4j
public class InnerService {

    private byte[] body;

    @PostConstruct
    public void init() {
        try {
            body = IOUtils.toByteArray(ResourceUtils.getFile("classpath:bee.ico").toURI());
        } catch (Exception e) {
            e.printStackTrace();
            log.error("read bee.ico error", e);
        }
    }

    public Flow doInner(String url, HttpResponseExt response) {
        if ("/ping".equals(url)) {
            response.setBody("pong");
            return Flow.END;

        }
        if ("/favicon.ico".equals(url)) {
            DefaultFullHttpResponse httpResponse = new DefaultFullHttpResponse(HttpVersion.HTTP_1_0, HttpResponseStatus.OK, Unpooled.copiedBuffer(body));
            httpResponse.headers().add("Content-Type", "image/x-icon");
            response.setFullHttpResponse(httpResponse);
            return Flow.END;
        }
        return Flow.GO;
    }
}
