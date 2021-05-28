package com.github.xhrg.bee.gateway.inner;

import com.github.xhrg.bee.gateway.api.Flow;
import com.github.xhrg.bee.gateway.http.HttpResponseExt;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.http.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.InputStream;

@Component
@Slf4j
public class InnerService {

    private byte[] body = new byte[0];

    @PostConstruct
    public void init() {
        try {
            ClassPathResource classPathResource = new ClassPathResource("bee.ico");
            InputStream inputStream = classPathResource.getInputStream();
            body = IOUtils.toByteArray(inputStream);
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
