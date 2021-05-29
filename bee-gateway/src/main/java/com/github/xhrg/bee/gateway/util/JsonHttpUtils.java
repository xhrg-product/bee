package com.github.xhrg.bee.gateway.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.ValueFilter;
import com.github.xhrg.bee.gateway.http.HttpRequestExt;
import io.netty.handler.codec.http.DefaultFullHttpRequest;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpVersion;
import org.apache.commons.lang3.StringUtils;

public class JsonHttpUtils {

//    public static String toNew(String temp, HttpRequestExt httpRequestExt) {
//        return "value";
//    }
//
//    public static void main(String[] args) {
//        DefaultFullHttpRequest defaultFullHttpRequest = new DefaultFullHttpRequest(HttpVersion.HTTP_1_0, HttpMethod.GET, "/a/a");
//        defaultFullHttpRequest.content().writeBytes("{\"aa\":\"bodydddd\"}".getBytes());
//        defaultFullHttpRequest.headers().add("bb", "headerbb");
//        String temp = "{\"aa\":\"$body.aa\",\"bb\":\"$header.bb\"}";
//        String value = JSON.toJSONString(JSON.parseObject(temp), new JsonHttpUtils.SimpleValueFilter(defaultFullHttpRequest));
//        System.out.println(value);
//    }
//
//    static class SimpleValueFilter implements ValueFilter {
//        FullHttpRequest fullHttpRequest;
//
//        SimpleValueFilter(FullHttpRequest fullHttpRequest) {
//            this.fullHttpRequest = fullHttpRequest;
//        }
//
//        public Object process(Object object, String name, Object value) {
//            String valueString = value.toString();
//            if (valueString.startsWith("$body.")) {
//                String key = StringUtils.remove(valueString, "$body.");
//                String body = "{\"aa\":\"bodydddd\"}";//mock httpbody
//                String result = JSON.parseObject(body).getString(key);
//                return result;
//            }
//            if (valueString.startsWith("$header.")) {
//                String key = StringUtils.remove(valueString, "$header.");
//                String result = fullHttpRequest.headers().get(key);
//                return result;
//            }
//            return value;
//        }
//    }
}
