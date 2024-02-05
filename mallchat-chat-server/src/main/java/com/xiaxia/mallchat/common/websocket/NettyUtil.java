package com.xiaxia.mallchat.common.websocket;

import io.netty.channel.Channel;
import io.netty.util.Attribute;
import io.netty.util.AttributeKey;

/**
 * @author xfl
 * @date 2024/1/21 13:40
 */
public class NettyUtil {

    public static AttributeKey<String> TOKEN = AttributeKey.valueOf("token");
    public static AttributeKey<String> IP = AttributeKey.valueOf("ip");

    public static <T> T getAttr(Channel channel, AttributeKey<T> key) {
        Attribute<T> attr = channel.attr(key);
        return attr.get();
    }

    public static <T> void setAttr(Channel channel, AttributeKey<T> key, T value) {
        Attribute<T> attr = channel.attr(key);
        attr.set(value);
    }
}
