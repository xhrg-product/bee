package com.github.xhrg.bee.gateway.cache;

import io.netty.channel.Channel;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

//netty的Channel链接需要维护主，逻辑是这样的。
//当前端有一个请求过来，那么会创建一个链接，然后会把这个链接到后端做一个双向绑定。
@Component
public class ChannelCache {

    private static Map<String, Channel> keyIsFront = new ConcurrentHashMap<String, Channel>();

    private static Map<String, Channel> keyIsBack = new ConcurrentHashMap<String, Channel>();

    //缓存2个链接
    public void put2Channel(Channel front, Channel back) {
        System.out.println(back.id().asLongText());
        keyIsFront.put(front.id().asLongText(), back);
        keyIsBack.put(back.id().asLongText(), front);
    }

    public Channel getByFront(Channel front) {
        return keyIsFront.get(front.id().asLongText());
    }

    public Channel getByBack(Channel back) {
        System.out.println(back.id().asLongText());
        return keyIsBack.get(back.id().asLongText());
    }

}
